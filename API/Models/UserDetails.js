const mongoose = require('mongoose');

const UserSchema = mongoose.Schema({
    userid:{type:Number,required:true},
    username:{type:String,required:true},
    password:{type:String,required:true}
    
});

module.exports=mongoose.model('UserDetails',UserSchema);