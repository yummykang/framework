# maven的archetype,可以install然后直接generate生成项目结构
## 1.springmvc
## 2.spring data jpa
### 1）utils包下jpa工具类用于简化jpa自身的动态specification查询
### 2）具体使用如下：
      JpaHelper<Team> helper = new JpaHelper<>();
      Specification<Team> spec = Specifications.where(helper.eq("status", search.getStatus())).
                      // 对应相应的查询条件
                      and(helper.eq("teamType", search.getTeamType())).
                      and(helper.gte("insDate", DateUtils.getStart(search.getStartTime()))).
                      and(helper.lte("insDate", DateUtils.getEnd(search.getEndTime()))).
                      and(helper.like("teamName", search.getTeamName()));
### 3）spring-data-jpa-extra,类mybatis查询，项目地址https://github.com/slyak/spring-data-jpa-extra
## 3.swagger
## 4.restful



# 关于linux下搭建nexus私服，并把相应的archetype install到私服上
## 1.下载及安装nexus，具体见官网文档，3.0版本在linux下安装比较简单，基本是傻瓜式的安装，当然如果想编译安装也可以
## 2.将archetype install至nexus中
### 1）需要配置maven，settings.xml（maven的配置文件）及pom.xml（项目的文件）
### 2）settings.xml中添加如下配置（在节点servers下）
       <server>
           <id>maven-snapshots</id>
           <username>admin</username>
           <password>admin123</password>	
       </server>        
       <server>		
           <id>maven-releases</id>		
           <username>admin</username>		
           <password>admin123</password>
       </server>
### 3）配置pom.xml文件，需要注意的地方是id需要跟上面server中配置的一样
    <distributionManagement>
        <repository>
            <id>maven-snapshots</id>
            <name>nexus</name>
            <url>http://192.168.241.129:8081/repository/maven-snapshots/</url>
        </repository>
    </distributionManagement>