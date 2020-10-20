#创建事实表并插入数据fact_order.csv
DROP TABLE IF EXISTS DEFAULT.fact_order;
create table DEFAULT.fact_order
(
    time_key string，
    product_key string，
    salesperson_key string，
    custom_key string，
    quantity_ordered bigint，
    order_dollars bigint，
    cost_dollars bigint
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '，'
STORED AS TEXTFILE;
load data local inpath 'fact_order.csv' overwrite into table DEFAULT.fact_order;

#创建日期维表day_dim并插入数据dim_day.csv：
DROP TABLE IF EXISTS DEFAULT.dim_day;
create table DEFAULT.dim_day
(
    day_key string，
    full_day string，
    month_name string，
    quarter string，
    year string
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '，'
STORED AS TEXTFILE;
load data local inpath 'dim_day.csv' overwrite into table DEFAULT.dim_day;

#创建售卖员的维表salesperson_dim并插入数据dim_salesperson.csv：
DROP TABLE IF EXISTS DEFAULT.dim_salesperson;
create table DEFAULT.dim_salesperson
(
    salesperson_key string，
    salesperson string，
    salesperson_id string，
    region string，
    region_code string
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '，'
STORED AS TEXTFILE;
load data local inpath 'dim_salesperson.csv' overwrite into table DEFAULT.dim_salesperson;

#创建客户维表 custom_dim并插入数据dim_custom.csv：
DROP TABLE IF EXISTS DEFAULT.dim_custom;
create table DEFAULT.dim_custom
(
    custom_key string，
    custom_name string，
    custorm_id string，
    headquarter_states string，
    billing_address string，
    billing_city string，
    billing_state string，
    industry_name string
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '，'
STORED AS TEXTFILE;
load data local inpath 'dim_custom.csv' overwrite into table DEFAULT.dim_custom;

#创建产品维表并插入数据dim_product.csv：
DROP TABLE IF EXISTS DEFAULT.dim_product;
create table DEFAULT.dim_product 
(
    product_key string，
    product_name string，
    product_id string，
    product_desc string，
    sku string，
    brand string，
    brand_code string，
    brand_manager string，
    category string，
    category_code string
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '，'
STORED AS TEXTFILE;
load data local inpath 'dim_product.csv' overwrite into table DEFAULT.dim_product;

