UPDATE DRAFT_ITEM_VALUES
SET 
   ITEM_VALUE = ?
 , ITEM_STATUS = ?
 , INSERT_USER = ?
 , INSERT_DATETIME = ?
 , UPDATE_USER = ?
 , UPDATE_DATETIME = ?
 , DELETE_FLAG = ?
WHERE 
DRAFT_ID = ?
 AND ITEM_NO = ?
 AND TYPE_ID = ?
;