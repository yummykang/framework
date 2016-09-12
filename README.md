# maven的archetype,可以install然后直接generate生成项目结构
## 1.springmvc
## 2.spring data jpa
### 1）utils包下jpa工具类用于简化jpa自身的动态specification查询
### 2）具体使用如下：
     `JpaHelper<Team> helper = new JpaHelper<>();
      Specification<Team> spec = Specifications.where(helper.eq("status", search.getStatus())).
                      // 对应相应的查询条件
                      and(helper.eq("teamType", search.getTeamType())).
                      and(helper.gte("insDate", DateUtils.getStart(search.getStartTime()))).
                      and(helper.lte("insDate", DateUtils.getEnd(search.getEndTime()))).
                      and(helper.like("teamName", search.getTeamName()));`
### 3）spring-data-jpa-extra,类mybatis查询，项目地址https://github.com/slyak/spring-data-jpa-extra
## 3.swagger
## 4.restful