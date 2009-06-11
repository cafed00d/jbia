-- *****************************************************************************
--  Copyright 2008, Javid Jamae and Peter Johnson
-- 
--  Licensed under the Apache License, Version 2.0 (the "License"); you may not
--  use this file except in compliance with the License. You may obtain a copy
--  of the License at
-- 
--     http://www.apache.org/licenses/LICENSE-2.0
--  
--  Unless required by applicable law or agreed to in writing, software
--  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
--  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
--  License for the specific language governing permissions and limitations
--  under the License.
-- *****************************************************************************
--
-- Recreates the MySQL database and the database owner.
--

DROP DATABASE ${db.database};
DROP USER '${db.user}'@'localhost';
CREATE DATABASE ${db.database};
GRANT ALL PRIVILEGES ON ${db.database}.* TO '${db.user}'@'localhost' IDENTIFIED BY '${db.password}' WITH GRANT OPTION;

