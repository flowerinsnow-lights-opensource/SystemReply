# SystemReply
Bukkit/系统自动回复

# 配置
一切条目位于`entries.yml`的根节点下

基本配置如下
```yaml
# 路径名 同时作为条目名
example:
  # 匹配规则列表
  # <i>开头为不区分大小写
  # S: 开头的为普通匹配，没有则默认该匹配
  # R: 开头为正则匹配
  # SE: 开头为精准普通匹配
  # 不提供精通正则匹配，请使用^和$元字符
  # 例如：
  # 'SE:HelloWorld' - 表示精准匹配HelloWorld
  # '<i>R:服务器的[0-9]{1,3}名玩家你们好' - 表示不区分大小写该正则表达式
  patterns:
    - '菜单命令'
    - 'SE:怎么打开菜单'
    - '<i>R:打开菜单的(命|指)令是/(menu|cd)还是/(menu|cd)(\?|？)?'
  # 回复消息；变量：
  # %(prefix) - 前缀，可在messages.yml配置
  # 可选，默认不发送消息
  replies:
    - %(prefix)&b菜单命令：&e/menu
  # 是否允许继续匹配其他消息，允许多次匹配并多次回复，根据条目顺序进行判断，直到pass为false为止
  # 可选，默认true
  pass: true
  # 是否在判断时异步，用于大规模匹配
  # 可选，默认false
  async: false
  # 是否阻止该消息发送出去
  # 可选，默认false
  block: false
```