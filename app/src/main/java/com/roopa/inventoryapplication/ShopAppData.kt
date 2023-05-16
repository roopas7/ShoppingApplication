package com.roopa.inventoryapplication

class ShopAppData {

    var id:String? = null
    var pName:String? = null
    var pDesc: String? = null
    var pImage: String? = null

    constructor(){}

    constructor(id: String, pName: String, pDesc: String, pImage: String){
        this.id = id
        this.pName = pName
        this.pDesc = pDesc
        this.pImage = pImage
    }
}