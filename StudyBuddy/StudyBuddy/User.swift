//
//  User.swift
//  StudyBuddy
//
//  Created by Madushani Lekam Wasam Liyanage on 10/15/16.
//  Copyright © 2016 C4Q. All rights reserved.
//

import Foundation

class User {
    
    let cunyId: Int
    let password: String
    let fristName: String
    let lastName: String
    
    init(cunyId: Int, password: String, firstName: String, lastName: String) {
        self.cunyId = cunyId
        self.password = password
        self.fristName = firstName
        self.lastName = lastName
    }
    
    convenience init?(withDict dict: [String:Any]) {
        
        if let cunyId = dict["cunyId"] as? Int ,
            let password = dict["password"] as? String,
            let firstName = dict["firstName"] as? String,
            let lastName = dict["lastName"] as? String {
            
            self.init(cunyId: cunyId, password: password, firstName: firstName, lastName: lastName)
        }
        else {
            return nil
        }
        
    }
    
    
    
    
    
    
    
    
    
    
    
}
