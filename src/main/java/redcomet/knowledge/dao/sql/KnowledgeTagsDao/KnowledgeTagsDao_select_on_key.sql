SELECT * FROM KNOWLEDGE_TAGS
 WHERE 
KNOWLEDGE_ID = ?
 AND TAG_ID = ?
 AND DELETE_FLAG = 0;
;
