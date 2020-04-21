package org.craftercms.blueprints.headless

import org.apache.commons.lang3.StringUtils
import org.craftercms.engine.service.UrlTransformationService
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.search.sort.FieldSortBuilder
import org.elasticsearch.search.sort.SortOrder

class SearchContentHelper {
    static final String JOB_CONTENT_TYPE_QUERY = "content-type:\"/page/jobpage\""
    static final String HOME_CONTENT_TYPE_QUERY = "content-type:\"/page/home\""
    static final String INTRO_CONTENT_TYPE_QUERY = "content-type:\"/page/introductiondetail\""
   
    static final String[] JOB_HIGHLIGHT_FIELDS = ["title_s", "item_o.item.content_html"]
    static final String[] HOME_HIGHLIGHT_FIELDS = ["title_s", "descripttion_t"]
    static final String[] INTRO_HIGHLIGHT_FIELDS = ["title_s", "contents_html"]
    
    static final int DEFAULT_START = 0
    static final int DEFAULT_ROWS = 10
    
    def elasticsearch
    UrlTransformationService urlTransformationService
    
    String jobContentQuery = JOB_CONTENT_TYPE_QUERY
    String homeContentQuery = HOME_CONTENT_TYPE_QUERY
    String introContentQuery = INTRO_CONTENT_TYPE_QUERY
    
    SearchContentHelper(elasticsearch, UrlTransformationService urlTransformationService) {
        this.elasticsearch = elasticsearch
        this.urlTransformationService = urlTransformationService
    }
    
    def searchJobs(String userTerm, start = DEFAULT_START, rows = DEFAULT_ROWS) {
        def q = "${jobContentQuery}"
        
        if (userTerm) {
            if(!userTerm.contains(" ")) {
                userTerm = "${userTerm}~1 OR *${userTerm}*"
            }
            
            def userTermQuery = "(title_s:(${userTerm}) OR item_o.item.content_html:(${userTerm}))"
            q = "${q} AND ${userTermQuery}"
        }
        
        def highlighter = SearchSourceBuilder.highlight()
        JOB_HIGHLIGHT_FIELDS.each{ field -> highlighter.field(field) }
        
        def builder = new SearchSourceBuilder()
          .query(QueryBuilders.queryStringQuery(q))
          .from(start)
          .size(rows)
          .highlighter(highlighter)
          
        def result = elasticsearch.search(new SearchRequest().source(builder))
        
        if (result) {
          return processUserSearchJobResults(result)
        } else {
          return []
        }
    }
    
    def processUserSearchJobResults(result) {
        def jobs = []
        def hits = result.hits.hits
        
        if (hits) {
            hits.each {hit -> 
                def doc = hit.getSourceAsMap()
                def job = [:]
                    job.title = doc.title_s
                    job.type = doc.type_s
                    job.duration = doc.duration_dt
                    job.url = urlTransformationService.transform("storeUrlToRenderUrl", doc.localId)
                    job.image = doc.image_s
                
                if (hit.highlightFields) {
                    def jobHighlights = hit.highlightFields.values()*.getFragments().flatten()*.string()
                    if (jobHighlights) {
                         def highlightValues = []
                         
                         jobHighlights.each {value -> 
                             highlightValues << value
                         }
                         
                         job.highlight = StringUtils.join(highlightValues, "... ")
                         job.highlight = StringUtils.strip(job.highlight)
                    }
                }
                jobs << job
            }
        }
        
        return jobs
    }
    
    def searchHomes(String userTerm , start = DEFAULT_START, rows = DEFAULT_ROWS) {
        def q = "${homeContentQuery}"
        
        if (userTerm) {
            if(!userTerm.contains(" ")) {
                userTerm = "${userTerm}~1 OR *${userTerm}*"
            }
            
            def userTermQuery = "(title_s:(${userTerm}))"
            q = "${q} AND ${userTermQuery}"
        }
        
        def highlighter = SearchSourceBuilder.highlight()
        HOME_HIGHLIGHT_FIELDS.each{ field -> highlighter.field(field) }
        
        def builder = new SearchSourceBuilder()
          .query(QueryBuilders.queryStringQuery(q))
          .from(start)
          .size(rows)
          .highlighter(highlighter)
          
        def result = elasticsearch.search(new SearchRequest().source(builder))
        
        if (result) {
          return processUserSearchHomeResults(result)
        } else {
          return []
        }
        
    }
    
    def processUserSearchHomeResults(result) {
        def homes = []
        def hits = result.hits.hits
        
        if (hits) {
            hits.each {hit -> 
                def doc = hit.getSourceAsMap()
                def home = [:]
                    home.title = doc.title_s
                    home.desc = doc.descripttion_t
                    home.url = urlTransformationService.transform("storeUrlToRenderUrl", doc.localId)
                
                if (hit.highlightFields) {
                    def homeHighlights = hit.highlightFields.values()*.getFragments().flatten()*.string()
                    if (homeHighlights) {
                         def highlightValues = []
                         
                         homeHighlights.each {value -> 
                             highlightValues << value
                         }
                         
                         home.highlight = StringUtils.join(highlightValues, "... ")
                         home.highlight = StringUtils.strip(home.highlight)
                    }
                }
                homes << home
            }
        }
        
        return homes
    }
    
    def searchIntros(String userTerm, start = DEFAULT_START, rows = DEFAULT_ROWS) {
        def q = "${introContentQuery}"
        
        if (userTerm) {
            if(!userTerm.contains(" ")) {
                userTerm = "${userTerm}~1 OR *${userTerm}*"
            }
            
            def userTermQuery = "(title_s:(${userTerm}) OR contents_html:(${userTerm}))"
            q = "${q} AND ${userTermQuery}"
        }
        
        def highlighter = SearchSourceBuilder.highlight()
        INTRO_HIGHLIGHT_FIELDS.each{ field -> highlighter.field(field) }
        
        def builder = new SearchSourceBuilder()
          .query(QueryBuilders.queryStringQuery(q))
          .from(start)
          .size(rows)
          .highlighter(highlighter)
          
        def result = elasticsearch.search(new SearchRequest().source(builder))
        
        if (result) {
          return processUserSearchIntroResults(result)
        } else {
          return []
        }
    }
    
    def processUserSearchIntroResults(result) {
        def intros = []
        def hits = result.hits.hits
        
        if (hits) {
            hits.each { hit ->
                def doc = hit.getSourceAsMap()
                def intro = [:]
                    intro.title = doc.title_s
                    intro.url = urlTransformationService.transform("storeUrlToRenderUrl", doc.localId)
                    intro.image = doc.thumbail_s
                
                if (hit.highlightFields) {
                    def introHighlights = hit.highlightFields.values()*.getFragments().flatten()*.string()
                    if (introHighlights) {
                        def highlightValues = []
                        
                        introHighlights.each {value -> 
                             highlightValues << value
                        }
                        
                        intro.highlight = StringUtils.join(highlightValues, "... ")
                        intro.highlight = StringUtils.strip(intro.highlight)
                    }
                }
                intros << intro
            }
        }
        
        return intros
    }

}