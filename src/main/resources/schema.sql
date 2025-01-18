CREATE type IF NOT EXISTS "jsonb" AS json;
CREATE ALIAS IF NOT EXISTS json_build_object FOR "org.capps.testutil.JSONFunctions.jsonBuildObject";
CREATE ALIAS IF NOT EXISTS jsonb_set FOR "org.capps.testutil.JSONFunctions.jsonbSet";
CREATE ALIAS IF NOT EXISTS jsonb_extract_path FOR "org.capps.testutil.JSONFunctions.jsonbExtractPath";


CREATE TABLE IF NOT EXISTS customers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15),
    address VARCHAR(255),
    location JSONB,
    delivery_base_price NUMERIC(10, 2)
);