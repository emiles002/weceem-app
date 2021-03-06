/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.weceem.auth

/**
 * User for user account
 * @author generated by plugin script
 */
class CMSUser {
    static transients = ['springSecurityService',"pass", 'roleIds','roleAuthorities']
    static hasMany = [authorities: CMSRole]

    def springSecurityService
    
    String username
    String firstName
    String lastName
    String passwd
    Boolean enabled = false

    String email
    Boolean emailShow = false

    /** description */
    String description = "" 

    /** plain password to create a MD5 password */
    String pass = "secret"

    static mappingRole = {
        cache usage: 'read-write'
        username index: 'userName_Idx'
    }

    static constraints = {
        username(blank: false, unique: true, maxSize:40)
        firstName(nullable: true, maxSize:40)
        lastName(nullable: true, maxSize:40)
        description(nullable: true, maxSize:80)
        email(email:true, nullable: true, maxSize:40)
        passwd(blank: false, nullable: false, maxSize:50)
        pass(blank: false, nullable: false, maxSize:30) // This is just for UI form submission
        enabled()
    }

    def getRoleAuthorities() {
        return authorities*.authority
    }
    
    List getRoleIds() {
        authorities.collect { it.id }
    }
    
    void setRoleIds(List ids) {
        this.authorities = []
        this.authorities = ids.collect { CMSRole.get(it) }
    }
    
    void setPass(String newPass) {
        passwd = springSecurityService.encodePassword(newPass)
    }
}