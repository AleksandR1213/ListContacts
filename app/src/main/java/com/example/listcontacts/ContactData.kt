package com.example.listcontacts

class ContactData(var id : Int,
                  private var name : String,
                  private var lastName : String,
                  private var dateBorn : String,
                  private var number : String) {

    var data_name = name
    var data_lastname = lastName
    var data_dateborn = dateBorn
    var data_number = number
}