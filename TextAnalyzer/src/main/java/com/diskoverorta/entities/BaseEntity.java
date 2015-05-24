package com.diskoverorta.entities;

import java.util.List;

/**
 * Created by praveen on 15/10/14.
 * Edited by subhasis : removed List<String> getEntities(List<List<CoreLabel>> sentTags) 
 */
public interface BaseEntity
{
    public List<String> getEntities(String sentence);    
}
