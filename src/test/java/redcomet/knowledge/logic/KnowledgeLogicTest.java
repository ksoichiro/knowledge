package redcomet.knowledge.logic;

import static redcomet.common.test.AssertEx.eqdb;

import java.util.ArrayList;
import java.util.List;

import net.arnx.jsonic.JSON;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import redcomet.common.log.Log;
import redcomet.common.log.LogFactory;
import redcomet.common.test.Order;
import redcomet.common.test.OrderedRunner;
import redcomet.common.util.RandomUtil;
import redcomet.knowledge.TestCommon;
import redcomet.knowledge.dao.KnowledgesDao;
import redcomet.knowledge.entity.KnowledgesEntity;
import redcomet.knowledge.searcher.impl.LuceneSearcher;
import redcomet.ormapping.common.DBUserPool;

@RunWith(OrderedRunner.class)
public class KnowledgeLogicTest extends TestCommon {
	/** ログ */
	private static Log LOG = LogFactory.getLog(KnowledgeLogicTest.class);

	
	private static List<KnowledgesEntity> list = new ArrayList<>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		initData();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Order(order= 1)
	public void testInsert() throws Exception {
		DBUserPool.get().setUser(loginedUser.getUserId());

		KnowledgesEntity entity = new KnowledgesEntity();
		entity.setTitle("Test1");
		entity.setContent("テストだよ");
		KnowledgeLogic logic = KnowledgeLogic.get();
		entity = logic.insert(entity, null, new ArrayList<Long>(), loginedUser);
		
		// publicFlagは指定しないでDBに保存すると、公開になる
		entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PUBLIC);
		
		KnowledgesEntity saved = KnowledgesDao.get().selectOnKey(entity.getKnowledgeId());
		eqdb(entity, saved);
		
		entity.setInsertUserName(loginedUser.getLoginUser().getUserName());
		saved = KnowledgesDao.get().selectOnKeyWithUserName(entity.getKnowledgeId());
		eqdb(entity, saved);
		entity.setLikeCount((long) 0);
		list.add(entity);
	}

	@Test
	@Order(order= 2)
	public void testUpdate() throws Exception {
		DBUserPool.get().setUser(loginedUser2.getUserId());

		KnowledgesEntity entity = new KnowledgesEntity();
		entity.setTitle(RandomUtil.randamGen(64));
		entity.setContent(RandomUtil.randamGen(1024));
		KnowledgeLogic logic = KnowledgeLogic.get();
		entity = logic.insert(entity, null, new  ArrayList<Long>(), loginedUser2);
		// publicFlagは指定しないでDBに保存すると、公開になる
		entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PUBLIC);
		KnowledgesEntity saved = KnowledgesDao.get().selectOnKey(entity.getKnowledgeId());
		eqdb(entity, saved);
		
		entity.setTitle(RandomUtil.randamGen(64));
		entity.setContent(RandomUtil.randamGen(1024));
		entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PRIVATE);
		logic.update(entity, null, new  ArrayList<Long>(), loginedUser2);

		saved = KnowledgesDao.get().selectOnKeyWithUserName(entity.getKnowledgeId());
		entity.setInsertUserName(loginedUser2.getLoginUser().getUserName());
		eqdb(entity, saved);
		entity.setLikeCount((long) 0);
		list.add(entity);
	}

	@Test
	@Order(order= 3)
	public void testSearchKnowledge() throws Exception {
//		KnowledgesEntity entity = new KnowledgesEntity();
//		entity.setKnowledgeId(1);
//		entity.setTitle("Test1");
//		entity.setContent("テストだよ");
//		entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PUBLIC);
//		entity.setDeleteFlag(INT_FLAG.OFF.getValue());
		List<KnowledgesEntity> checks = new ArrayList<KnowledgesEntity>();
		checks.add(list.get(0));
		
		KnowledgeLogic logic = KnowledgeLogic.get();
		List<KnowledgesEntity> entities = logic.searchKnowledge(null, loginedUser, 0, 100);
		LOG.info(JSON.encode(entities, true));
		eqdb(checks, entities);
		
		checks = new ArrayList<KnowledgesEntity>();
		list.get(1).setContent(StringUtils.abbreviate(list.get(1).getContent(), LuceneSearcher.CONTENTS_LIMIT_LENGTH));
		checks.add(list.get(1)); //スコア上
		checks.add(list.get(0));
		
		entities = logic.searchKnowledge(null, loginedUser2, 0, 100);
		LOG.info(JSON.encode(entities, true));
		eqdb(checks, entities);
		
		checks = new ArrayList<KnowledgesEntity>();
		checks.add(list.get(0));
		
		entities = logic.searchKnowledge("テスト", loginedUser2, 0, 100);
		list.get(0).setContent("<span class=\"mark\">テスト</span>だよ");
		
		LOG.info(JSON.encode(entities, true));
		eqdb(checks, entities);
		
	}

}
