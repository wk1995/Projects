记账薄 数据库

1、cashbook App 数据库

类别：billCategory

|    类别名    |  创建时间  |  父类别  | 备注 |
| :----------: | :--------: | :------: | ---- |
| categoryName | createTime | parentId | note |

支出，收入，内部转账    为 根类别

支出： 餐，零食，房租，电器，恋爱，转帐，水果

收入： 工资，理财



2、账户：account

|   账户名    |  创建时间  |  金额  | 备注 |
| :---------: | :--------: | :----: | ---- |
| accountName | createTime | amount | note |

微信：

支付宝

现金

银行卡




3 、收支记录 tradeRecode
    


| 交易记录名 | 交易时间  |   账户    |    类别    |  金额  |   备注    |   交易对象   |
| :--------: | :-------: | :-------: | :--------: | :----: | :-------: | :----------: |
| tradeName  | tradeTime | accountId | categoryId | Amount | tradeNote | tradeAccount |



























