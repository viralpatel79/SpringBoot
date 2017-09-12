SET FOREIGN_KEY_CHECKS=0;
 

CREATE TABLE `users` (
  `user_id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL DEFAULT '',
  `encrypted_password` varchar(255) NOT NULL DEFAULT '',
  `reset_password_token` varchar(255) DEFAULT NULL,
  `reset_password_sent_at` datetime DEFAULT NULL,
  `remember_created_at` datetime DEFAULT NULL,
  `sign_in_count` int(11) NOT NULL DEFAULT '0',
  `current_sign_in_at` datetime DEFAULT NULL,
  `last_sign_in_at` datetime DEFAULT NULL,
  `current_sign_in_ip` varchar(255) DEFAULT NULL,
  `last_sign_in_ip` varchar(255) DEFAULT NULL,   
  `role` varchar(255) DEFAULT 'customer',
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL, 
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `address` text,
  `bio` text,
  `type` varchar(255) DEFAULT 'Customer',
  `stripe_customer_id` varchar(255) DEFAULT NULL,
  `visible` tinyint(1) DEFAULT '1',
  `referral` text,
  `referral_admin` bigint(11) unsigned DEFAULT NULL,
  `school_id` bigint(11) unsigned DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `paypal_address` varchar(255) DEFAULT NULL,
  `rep_link` varchar(255) DEFAULT NULL,
  `class_of_year` varchar(255) DEFAULT NULL,
  `profile_picture` varchar(255) DEFAULT NULL,
  `facebook_profile_picture` varchar(255) DEFAULT NULL,
  `aasm_state` varchar(255) DEFAULT NULL,
  `api` tinyint(1) DEFAULT '0',
  `manager_id` bigint(11) unsigned DEFAULT NULL,
  `calendar_id` varchar(255) DEFAULT NULL,
  `available_for_appointment` tinyint(1) DEFAULT '1',
  `return_address_1` varchar(255) DEFAULT NULL,
  `return_address_2` varchar(255) DEFAULT NULL,
  `return_zip_code` varchar(255) DEFAULT NULL,
  `return_city` varchar(255) DEFAULT NULL,
  `return_state` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `referrer_id` bigint(11) unsigned DEFAULT NULL,
  `ref_link_visit_count` int(11) DEFAULT '0',
  `auth_provider` varchar(255) DEFAULT NULL,
  `auth_uid` varchar(255) DEFAULT NULL,
  `referral_code` varchar(255) DEFAULT NULL,
  `referrer_type` varchar(255) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(255) unsigned NOT NULL DEFAULT '1',
  `updated_by` bigint(20) unsigned NOT NULL DEFAULT '1',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `index_users_on_email` (`email`),
  UNIQUE KEY `index_users_on_reset_password_token` (`reset_password_token`),
  KEY `index_users_on_manager_id` (`manager_id`),
  KEY `index_users_on_school_id` (`school_id`),
  KEY `index_users_on_type` (`type`),
  KEY `fk_rails_92c9bd2db4` (`referrer_id`),
  KEY `index_users_on_auth_provider` (`auth_provider`),
  KEY `index_users_on_auth_uid` (`auth_uid`),
  KEY `index_users_on_referral_code` (`referral_code`),
  KEY `index_users_on_referrer_type` (`referrer_type`),
  CONSTRAINT `fk_rails_92c9bd2db4` FOREIGN KEY (`referrer_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

  


CREATE TABLE `access_tokens` (
  `token_id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) unsigned NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `token` varchar(255) NOT NULL,
  PRIMARY KEY (`token_id`),
  KEY `access_tokens_user_id_foreign` (`user_id`),
  CONSTRAINT `access_tokens_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=latin1;

INSERT INTO `users` (`user_id`, `email`, `encrypted_password`, `first_name`, `last_name`, `phone`, `role`, `reset_password_token`, `reset_password_sent_at`, `remember_created_at`,
`sign_in_count`, `current_sign_in_at`, `last_sign_in_at`, `current_sign_in_ip`, `last_sign_in_ip`, `start_date`, `end_date`, `visible`, `address`, `bio`, `type`, 
`stripe_customer_id`, `created_at`, `updated_at`, `api`, `available_for_appointment`, `ref_link_visit_count`, `school_id`, `manager_id`, `referral_admin`, `referrer_id`) 
VALUES ('1', 'viralpatel79@yahoo.com', '$2a$16$9KqdfSXEBJNVVwfiM27TZ.I0GgxLPop3pIvQnXRffeHPCCPwfQt8q', 'Viral', 'Patel', null, 'customer', null, null, null, 
'0', null, null, null, null, '2017-08-19 03:24:07', null, '1', null, null, null, 
null, '2017-08-19 03:24:07', '2017-08-19 03:24:07', '1', '1', '0', '1', '1', '1', '1');

CREATE TABLE `cities` (                                            
          `city_id` bigint(11) NOT NULL AUTO_INCREMENT,                            
          `name` varchar(255) DEFAULT NULL,                                
          `url_name` varchar(255) DEFAULT NULL,                            
          `state` varchar(255) DEFAULT NULL,                               
          `meta_description` text,                                         
          `about_us` text,                                                 
          `display` tinyint(1) DEFAULT NULL,                               
          `created_at` datetime DEFAULT NULL,                              
          `updated_at` datetime DEFAULT NULL,                              
          PRIMARY KEY (`city_id`),                                              
          UNIQUE KEY `index_cities_on_url_name` (`url_name`) USING BTREE,  
          KEY `index_cities_on_state` (`state`) USING BTREE,               
          KEY `index_cities_on_display` (`display`)                        
        ) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=latin1;
        
CREATE TABLE `schools` (                                                               
           `school_id` bigint(11) NOT NULL AUTO_INCREMENT,                                                                                                    
           `name` varchar(255) DEFAULT NULL,                                                    
           `created_at` datetime DEFAULT NULL,                                                  
           `updated_at` datetime DEFAULT NULL,                                                  
           `off_campus_image` varchar(255) DEFAULT NULL,                                        
           `off_campus_where` varchar(255) DEFAULT NULL,                                        
           `off_campus_time` varchar(255) DEFAULT NULL,                                         
           `buy_back` text,                                                                     
           `buy_back_image` varchar(255) DEFAULT NULL,                                          
           `full_name` varchar(255) DEFAULT NULL,                                               
           `buy_back_state` tinyint(1) DEFAULT '1',                                             
           `school_link` varchar(255) DEFAULT NULL,                                             
           `stripe_user_id` varchar(255) DEFAULT NULL,                                          
           `currency` varchar(255) DEFAULT NULL,                                                
           `about_us` text,                                                                     
           `sales_tax` decimal(6,3) DEFAULT NULL,                                                      
           `commission` decimal(6,3) DEFAULT '5.000',                                           
           `embed_code` text,                                                                   
           `closed_dialog_description` text,                                                    
           `closed_dialog_link_text` varchar(255) DEFAULT NULL,                                 
           `closed_dialog_link_uri` varchar(255) DEFAULT NULL,                                  
           `image` varchar(255) DEFAULT NULL,                                                   
           `requested_calendar_id` varchar(255) DEFAULT NULL,                                   
           `confirmed_calendar_id` varchar(255) DEFAULT NULL,                                   
           `estimate_tool` tinyint(1) DEFAULT '1',                                              
           `buying_state` varchar(255) DEFAULT NULL,                                            
           `city_id` bigint(11) DEFAULT NULL,                                                      
           `payment_method` int(11) NOT NULL DEFAULT '1',                                       
           `local_delivery` tinyint(1) DEFAULT '0',                                             
           `appointments_enabled` tinyint(1) DEFAULT '0',  
           `created_by` bigint(255) unsigned NOT NULL DEFAULT '1',
  		   `updated_by` bigint(20) unsigned NOT NULL DEFAULT '1',
  		   `deleted` tinyint(1) NOT NULL DEFAULT '0',                                     
           PRIMARY KEY (`school_id`),                                                                  
           KEY `index_schools_on_name` (`name`),                                                
           KEY `index_schools_on_deleted` (`deleted`),                                          
           KEY `index_schools_on_city_id` (`city_id`),                                          
           CONSTRAINT `fk_rails_52531ed86f` FOREIGN KEY (`city_id`) REFERENCES `cities` (`city_id`)  
         ) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=latin1;   

insert into `cities`(`city_id`,`name`,`url_name`,`state`,`meta_description`,`about_us`,`display`,`created_at`,`updated_at`) 
values ( 1,'New York','new_york','NY',NULL,NULL,0,'2016-08-25 14:53:11','2016-08-25 14:53:11');           

insert into `schools` (`school_id`, `deleted`, `name`, `created_at`, `updated_at`, `off_campus_image`, `off_campus_where`, `off_campus_time`, `buy_back`, `buy_back_image`, `full_name`, `buy_back_state`, `school_link`, `stripe_user_id`, `currency`, `about_us`, `sales_tax`, `commission`, `embed_code`, `closed_dialog_description`, `closed_dialog_link_text`, `closed_dialog_link_uri`, `image`, `requested_calendar_id`, `confirmed_calendar_id`, `estimate_tool`, `buying_state`, `city_id`, `payment_method`, `local_delivery`, `appointments_enabled`) 
values('1','1','UD','2016-11-02 21:00:06','2016-12-28 18:14:15',NULL,'','','',NULL,'UD | UNIVERSITY OF DALLAS','1',NULL,NULL,NULL,NULL,NULL,'0.080',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','rush_and_course_list','1','1','0','1');

CREATE TABLE `events` (                                    
          `event_id` bigint(11) NOT NULL AUTO_INCREMENT,                                                    
          `location` varchar(255) DEFAULT NULL,                    
          `user_id` bigint(11) DEFAULT NULL,                          
          `start_date` datetime DEFAULT NULL,                      
          `created_at` datetime DEFAULT NULL,                      
          `updated_at` datetime DEFAULT NULL,                      
          `end_date` datetime DEFAULT NULL,                        
          `school_id` bigint(11) DEFAULT NULL, 
          `created_by` bigint(255) unsigned NOT NULL DEFAULT '1',
  		  `updated_by` bigint(20) unsigned NOT NULL DEFAULT '1',
  		  `deleted` tinyint(1) NOT NULL DEFAULT '0',                       
          PRIMARY KEY (`event_id`),                                      
          KEY `index_events_on_deleted` (`deleted`),               
          KEY `index_events_on_school_id` (`school_id`),           
          KEY `index_events_on_start_date` (`start_date`)          
        ) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=latin1;
        
insert into `events`(`event_id`,`location`,`user_id`,`start_date`,`created_at`,`updated_at`,`end_date`,`school_id`,`created_by`,`updated_by`,`deleted`) 
values ( 1,'Mcelvaney Dorm Lounge','1','2013-12-13 07:00:00','2013-12-05 16:29:56','2014-04-22 02:30:04','2013-12-13 08:00:00','1','1','1','0');

SET FOREIGN_KEY_CHECKS=1;