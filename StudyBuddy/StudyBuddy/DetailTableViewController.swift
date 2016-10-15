//
//  DetailViewController.swift
//  StudyBuddy
//
//  Created by Madushani Lekam Wasam Liyanage on 10/15/16.
//  Copyright © 2016 C4Q. All rights reserved.
//

import UIKit

class DetailTableViewController: UITableViewController {
   
    @IBOutlet weak var locationLabel: UILabel!
    var studySessionsArray = [Session]()
    override func viewDidLoad() {
        super.viewDidLoad()
        
        for s in studySessions {
            if let session = Session(withDict: s) {
                studySessionsArray.append(session)
            }
        }
        // Do any additional setup after loading the view.
    }
    
    
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return studySessionsArray.count
    }
    
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "sessionIdentifier", for: indexPath)
        
        let studySessionAtIndexPath = self.studySessionsArray[indexPath.row]
        cell.textLabel?.text = studySessionAtIndexPath.topicOfStudy + " : " + studySessionAtIndexPath.locationName
        
      
        return cell
    }
    
    /*
     // MARK: - Navigation
     
     // In a storyboard-based application, you will often want to do a little preparation before navigation
     override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
     // Get the new view controller using segue.destinationViewController.
     // Pass the selected object to the new view controller.
     }
     */
    
}
