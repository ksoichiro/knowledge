UPDATE KNOWLEDGE_TAGS
SET 
   INSERT_USER = ?
 , INSERT_DATETIME = ?
 , UPDATE_USER = ?
 , UPDATE_DATETIME = ?
 , DELETE_FLAG = ?
WHERE 
KNOWLEDGE_ID = ?
 AND TAG_ID = ?
;
