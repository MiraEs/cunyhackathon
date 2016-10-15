//
//  LoginViewController.swift
//  StudyBuddy
//
//  Created by Ilmira Estil on 10/14/16.
//  Copyright © 2016 C4Q. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController, UITextFieldDelegate {
    
    var users = [User]()
    //let user = User?()
    var shouldLetUserLogin = Bool()
    var shouldGotoDetailView = Bool()
    
    
    @IBOutlet weak var cunyIdTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var incorrectDataLoginLabel: UILabel!
    
    @IBAction func loginPressed(_ sender: UIButton) {
        if checkCorrectLoginData() {
            print("Good")
            incorrectDataLoginLabel.isHidden = true
            shouldGotoDetailView = true
            performSegue(withIdentifier: "studySessionsIdentifier", sender: shouldGotoDetailView )
            
        }
        else {
            incorrectDataLoginLabel.isHidden = false
            shouldGotoDetailView = false
        }
        
        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        for aData in cunyStudents {
            if let data = User(withDict: aData) {
                users.append(data)
            }
            
        }
        
    }
    
    @nonobjc func textFieldDidEndEditing(_ textField: UITextField) -> Bool {
        return true
    }
    
    @discardableResult func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        print("should return")
        _ = self.validateLoginEntries()
        
        return true
        
    }
    
    func validateLoginEntries() -> Bool {
        var isValid = Bool()
        
        if let idCount = cunyIdTextField.text?.characters.count, let pwdCount = passwordTextField.text?.characters.count{
            if idCount == 8 && pwdCount >= 8 {
                isValid = true
            }
            else {
                isValid = false
            }
        }
        return isValid
    }
    
    
    
    func checkCorrectLoginData() -> Bool {
        var isCorrect = Bool()
        if textFieldDidEndEditing(cunyIdTextField) && textFieldDidEndEditing(passwordTextField) {
            
            if let id = cunyIdTextField.text, let pwd = passwordTextField.text {
                for aUser in users {
                    if Int(id) == aUser.cunyId && pwd == aUser.password {
                        isCorrect = true
                        break
                    }
                    else {
                        isCorrect = false
                    }
                }
                
            }
        }
        return isCorrect
    }
    
    // MARK: - Navigation
    
    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        if segue.identifier == "studySessionsIdentifier" {
            if let dvc = segue.destination as? DetailViewController {
                shouldGotoDetailView = true
            }
        }

        
    }
    
    
}
