-- *****************************************************************************
--  Copyright 2007, Javid Jamae and Peter Johnson
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
-- Populates the tables used for authorization and authentication. 
--

CREATE TABLE VUser (
   vname VARCHAR(30) NOT NULL,
   vpassword VARCHAR(30) NOT NULL,
   PRIMARY KEY(vname));
CREATE TABLE VRole (
   vname VARCHAR(30) NOT NULL,
   vrole VARCHAR(30) NOT NULL,
   PRIMARY KEY(vname));

INSERT INTO VUser (vname, vpassword) VALUES ('somebody' , 'special' );
INSERT INTO VUser (vname, vpassword) VALUES ('another' , 'person' );
INSERT INTO VRole (vname, vrole) VALUES ('somebody' , 'admin');
INSERT INTO VRole (vname, vrole) VALUES ('another' , 'user');
