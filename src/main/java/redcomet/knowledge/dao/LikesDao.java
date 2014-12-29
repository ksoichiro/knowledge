package redcomet.knowledge.dao;

import redcomet.di.Container;
import redcomet.di.DI;
import redcomet.di.Instance;
import redcomet.knowledge.dao.gen.GenLikesDao;

/**
 * いいね
 */
@DI(instance=Instance.Singleton)
public class LikesDao extends GenLikesDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;
	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static LikesDao get() {
		return Container.getComp(LikesDao.class);
	}
	
	
	public Long countOnKnowledgeId(Long knowledgeId) {
		String sql = "SELECT COUNT(*) FROM LIKES WHERE KNOWLEDGE_ID = ?";
		return super.executeQuerySingle(sql, Long.class, knowledgeId);
	}



}
