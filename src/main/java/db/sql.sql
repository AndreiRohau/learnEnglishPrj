CREATE SCHEMA 'new_schema' DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `users` (
 `user_id` int(11) NOT NULL AUTO_INCREMENT,
 `user_name` varchar(45) NOT NULL,
 `user_password` varchar(45) NOT NULL,
 `user_phrases_per_day` int(11) DEFAULT '1',
 `users_last_time` varchar(45) DEFAULT '0',
 `user_test_tried` int(255) DEFAULT '0',
 `rightAns` int(255) DEFAULT '0',
 `wrongAns` int(255) DEFAULT '0',
 PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8

CREATE TABLE 'phrases' (
 'phrase_id' int(11) NOT NULL AUTO_INCREMENT,
 'phrase_ru' varchar(45) DEFAULT NULL,
 'phrase_en' varchar(45) DEFAULT NULL,
 PRIMARY KEY ('phrase_id')
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8

CREATE TABLE 'user_phrasess' (
 'users_id' int(11) NOT NULL,
 'phrases_id' int(11) NOT NULL,
 KEY 'usr_id_eqlt_idx' ('users_id'),
 KEY 'phrase_id_eqlt_idx' ('phrases_id'),
 CONSTRAINT 'phrase_id_eqlt' FOREIGN KEY ('phrases_id') REFERENCES 'phrases' ('phrase_id') ON DELETE NO ACTION ON UPDATE NO ACTION,
 CONSTRAINT 'usr_id_eqlt' FOREIGN KEY ('users_id') REFERENCES 'users' ('user_id') ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE 'user_phrases_failedd' (
 'users_id' int(11) NOT NULL,
 'failed_phrase_id' int(11) NOT NULL,
 KEY 'usr_id_eqlt_idx' ('users_id'),
 KEY 'usr_id_eqltyy_idx' ('users_id'),
 KEY 'failed_phr_id_eqltyy_idx' ('failed_phrase_id'),
 CONSTRAINT 'failed_phr_id_eqltyy' FOREIGN KEY ('failed_phrase_id') REFERENCES 'phrases' ('phrase_id') ON DELETE NO ACTION ON UPDATE NO ACTION,
 CONSTRAINT 'usr_id_eqltyy' FOREIGN KEY ('users_id') REFERENCES 'users' ('user_id') ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8

