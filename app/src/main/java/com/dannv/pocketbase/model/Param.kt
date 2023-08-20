package com.dannv.pocketbase.model

import com.google.gson.annotations.SerializedName

data class Param(
	@SerializedName("identity")
	var indentity: String,
	@SerializedName("password")
	var password: String
)
data class LoginResponse (
	@SerializedName("token"  ) var token  : String? = null,
	@SerializedName("record" ) var record : User? = User()
)

data class Hihi (
	@SerializedName("hihi_name") var name : String? = "",
	@SerializedName("hihi_age") var age : String? = "",
)

data class BaseResponse (
	@SerializedName("id") var id : String? = "",
)
data class User (
	@SerializedName("id"              ) var id              : String?  = null,
	@SerializedName("collectionId"    ) var collectionId    : String?  = null,
	@SerializedName("collectionName"  ) var collectionName  : String?  = null,
	@SerializedName("username"        ) var username        : String?  = null,
	@SerializedName("verified"        ) var verified        : Boolean? = null,
	@SerializedName("emailVisibility" ) var emailVisibility : Boolean? = null,
	@SerializedName("email"           ) var email           : String?  = null,
	@SerializedName("created"         ) var created         : String?  = null,
	@SerializedName("updated"         ) var updated         : String?  = null,
	@SerializedName("name"            ) var name            : String?  = null,
	@SerializedName("avatar"          ) var avatar          : String?  = null
)