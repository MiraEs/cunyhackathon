//
//  Session.swift
//  StudyBuddy
//
//  Created by Madushani Lekam Wasam Liyanage on 10/15/16.
//  Copyright © 2016 C4Q. All rights reserved.
//

import Foundation

class Session {
    
    //    "id": "3",
    //    "topic_of_study": "Computer Algorithm I",
    //    "image_url": "http://cdn.archinect.net/images/1200x/vx/vxyq6uvi3lu0mu2r.jpg",
    //    "location_name": "Hunter College",
    //    "location_address": "695 Park Ave, New York, NY 10065",
    //    "distance": 0.01,
    //    "number_of_scholars": 5,
    //    "time_start": "3:00pm",
    //    "time_end": "4:00pm"
    //
    let id: String
    let topicOfStudy: String
    let imageUrl: String
    let locationName: String
    //    let locationAddress: String
    //    let distance: Double
    //    let numberOfScholars: Int
    //    let timeStart: String
    //    let timeEnd: String
    
    
    init(id: String, topicOfStudy: String, imageUrl: String, locationName: String) {
        //        , distance: Double,numberOfScholars: Int, timeStart: String, timeEnd: String
        self.id = id
        self.topicOfStudy = topicOfStudy
        self.imageUrl = imageUrl
        self.locationName = locationName
        //   self.locationAddress = locationAddress
        
    }
    
    convenience init?(withDict dict: [String:Any]) {
        
        if let id = dict["id"] as? String ,
            let topicOfStudy = dict["topic_of_study"] as? String,
            let imageUrl = dict["image_url"] as? String,
            let locationName = dict["location_name"] as? String {
            
            self.init(id: id, topicOfStudy: topicOfStudy, imageUrl: imageUrl, locationName: locationName)
        }
        else {
            return nil
        }
        
    }
    
   
    
}
