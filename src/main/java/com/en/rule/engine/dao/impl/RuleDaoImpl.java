package com.en.rule.engine.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.en.rule.engine.dao.IRuleDao;
import com.en.rule.engine.dao.rowmapper.RuleRowMapper;
import com.en.rule.engine.dao.rowmapper.RulesRowMapper;
import com.en.rule.engine.model.Rule;
/**
 * 
 * @author Suman Kumar
 *
 */
@Repository
public class RuleDaoImpl  implements IRuleDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(RuleDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	RuleIdGenerator ruleIdGenerator;

	@Override
	public Rule createRule(Rule rule) {
        rule.setId(ruleIdGenerator.generate());
		this.jdbcTemplate.update(RuleQueries.CREATE_RULE, rule.getId(), rule.getLowerBoundryOfDate(),rule.getUpperBoundryOfDate(),
				rule.getValue().getValue(),rule.getLowerBoundryOfInt(), rule.getUpperBoundryOfInt());
		LOG.info("Rule created with id  {}", rule.getId());
		return rule;
	}

	@Override
	public List<Rule> fatchAllRules() {
		return  this.jdbcTemplate.query(RuleQueries.FETCH_ALL_RULE, new RulesRowMapper());
	}
	
	@Override
	public Rule fatchRuleForRuleID(Long id) {
		String sql = RuleQueries.FETCH_ALL_RULE + " where id = " + id;
		return this.jdbcTemplate.query(sql, new RuleRowMapper());
	}
}