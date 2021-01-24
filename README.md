# iShare社区
##介绍

iShare社区是仿elasticsearch社区项目，技术栈Springboot、MyBatis 、Redis、Bootstrap、MySQL。主要功能有gitee第三方登录、发布、标签、评论、回复、通知、分页、七牛云图片上传、点赞、Markdowm富文本编辑、搜索。

## 技术栈

|  技术   |  作用   |
| --- | --- |
| SSM | 后端 |
|  Bootstrap、thymeleaf|前端|
| gitee | 第三方登录 |
| MySQL | 数据库 |
| Redis | 点赞 |
| Markdowm | 富文本编辑 |
| flyway | 数据库版本控制 |
| 七牛云 | 图片上传 |
## 资料
[Spring 文档](https://spring.io/guides)    
[Spring Web](https://spring.io/guides/gs/serving-web-content/)   
[es](https://elasticsearch.cn/explore)    
[Bootstrap](https://v3.bootcss.com/getting-started/)    
[Gitee OAuth](https://gitee.com/api/v5/oauth_doc#/)    
[Spring](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#boot-features-embedded-database-support)    
[菜鸟教程](https://www.runoob.com/mysql/mysql-insert-query.html)    
[Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#setting-attribute-values)    
[Spring Dev Tool](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#using-boot-devtools)  
[Spring MVC](https://docs.spring.io/spring/docs/5.0.3.RELEASE/spring-framework-reference/web.html#mvc-handlermapping-interceptor)  
[Markdown 插件](http://editor.md.ipandao.com/)   
[QiNiuCloud SDK](https://developer.qiniu.com/kodo/sdk/1239/java)  

## 工具
[Git](https://git-scm.com/download)   
[Visual Paradigm](https://www.visual-paradigm.com)    
[Flyway](https://flywaydb.org/getstarted/firststeps/maven)     
[ctotree](https://www.octotree.io/)   
[Table of content sidebar](https://chrome.google.com/webstore/detail/table-of-contents-sidebar/ohohkfheangmbedkgechjkmbepeikkej)    
[One Tab](https://chrome.google.com/webstore/detail/chphlpgkkbolifaimnlloiipkdnihall)    
[Live Reload](https://chrome.google.com/webstore/detail/livereload/jnihajbhpnppcggbcgedagnkighmdlei/related)  
[Postman](https://chrome.google.com/webstore/detail/coohjcphdfgbiolnekdpbcijmhambjff)

## 脚本

```bash
mvn flyway:migrate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```
