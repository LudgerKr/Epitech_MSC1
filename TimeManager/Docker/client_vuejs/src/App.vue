<template>
    <div id="#app" class="container-fluid vh-100" style="display: flex;flex-flow: column; padding-left:0;padding-right:0">
      <b-navbar v-if="$route.path !=='/'" type="dark" variant="warning" class="navbar-horizontal" style="max-height: 50%">
        <b-navbar-nav class="align-center">
          <b-navbar-brand href="#">
            <b-img :src="require('./assets/logotwo.jpg')" style="height: 40px "> </b-img>
          </b-navbar-brand >

          <b-nav-item  :user="user" @child-props="getUser" @click="goHome()">Home</b-nav-item>
          <b-nav-item v-if="user.role_id==2" @click="$bvModal.show('bv-modal-planing')">Planing</b-nav-item>
          <b-nav-item v-if="user.role_id==3">Formular</b-nav-item>
          <!-- USER for SHOW profile, edit, delete or SingOut-->
          <b-nav-item-dropdown>
            <template #button-content>
              <em>User</em>
            </template>
            <b-dropdown-item @click="$bvModal.show('bv-modal-profile')">Profile</b-dropdown-item>
            <b-dropdown-item @click="sign_out()">Sign Out</b-dropdown-item>
          </b-nav-item-dropdown>
          <b-nav-item @click="$bvModal.show('bv-modal-planing')">Planing</b-nav-item>
          <b-nav-item v-if="user.role_id==1" @click="$bvModal.show('bv-modal-analytic')" >Analytic</b-nav-item>
          <!-- Users for show all users for Admin-->
          <b-nav-item v-if="user.role_id!=3" :user="user" @child-props="getUser" @click="goTeamPage(user.team_id)">My team</b-nav-item>
          <!-- Admin create user-->
          <b-nav-item v-if="user.role_id==1" :user="user" @child-props="getUser" @click="createUser()">Create a new user</b-nav-item>
          <!-- CheckIn/CheckOut for Employe -->
          <b-nav-item v-if="user.role_id!=1" :user="user" @child-props="getUser" @click="$bvModal.show('bv-modal-check')">Check</b-nav-item>
          <!-- View all teams -->
          <b-nav-item v-if="user.role_id==1" :user="user" @child-props="getUser" @click="goTeams()">Teams</b-nav-item>
          <!-- View all users -->
          <b-nav-item v-if="user.role_id==1" :user="user" @child-props="getUser" @click="goUsers()">All Users</b-nav-item>


          <!-- POP-UP for User : Information and Edit -->
          <b-modal id="bv-modal-profile" title="User" hide-footer>
            <b-tabs content-class="mt-3">
              <!-- ONGLET Information -->
            <b-tab title="Information" active>
              <p>Name: {{user.firstname}}</p>
              <p>Lastname: {{user.lastname}} </p>
              <p>Username: {{user.username}}</p>
              <p>Email: {{user.email}}</p>
              <p>Role : {{user.role_id}}</p>
              <p>Team : {{user.team_id}}</p>
            </b-tab>
              <!-- ONGLET Edit-->
            <b-tab title="Edit">
              <b-form>
                <b-form-group label="Username" label-for="input_1">
                  <b-form-input id="input_1" type="text" v-model="username_edit"></b-form-input>
                </b-form-group>
                <b-form-group label="Email" label-for="input_2">
                  <b-form-input id="input_2" type="text" v-model="mail_edit" ></b-form-input>
                </b-form-group>
                <b-form-group label="New Password" label-for="input_3">
                  <b-form-input id="input_3" type="text" v-model="pwd_edit"  ></b-form-input>
                </b-form-group>
                <b-form-group label="Confirm New Password" label-for="input_4">
                  <b-form-input id="input_4" type="email" v-model="pwd_conf_edit"  ></b-form-input>
                </b-form-group>
              </b-form>
              <b-button-group right>
                <b-button variant="dark" @click="$bvModal.hide('bv-modal-profile')">Cancel</b-button>
                <b-button variant="success" @click="edit_user()">Edit</b-button>
                <b-button variant="danger" @click="delete_user()" >Delete</b-button>
              </b-button-group>
            </b-tab>
            </b-tabs>
          </b-modal>

          <!-- POP-UP Check In / Check Out-->
          <b-modal id="bv-modal-check" title="Check">
            <b-button id="btn_checkIn" block variant="primary" @click="setCheckIn()" >
              <b-icon icon="clock"></b-icon> Check In
            </b-button>
            <p>Check In:</p>
            <p v-if="timeIn!=null">{{parseDate(timeIn)}}</p>
            <b-button id="btn_checkOut" block variant="primary" @click="setCheckOut()" >
              <b-icon icon="clock"></b-icon> Check Out
            </b-button>
            <p>Check Out:</p>
            <p v-if="timeOut!=null">{{parseDate(timeOut)}}</p>
          </b-modal>

          <!-- POP-UP Analytic for Admin-->
          <b-modal id="bv-modal-analytic" title="Analytic">
            <b-form>
              <b-form-group label="Team ID" label-for="input_2">
                <b-form-input id="input_2" type="text" v-model="analytic_teamID" ></b-form-input>
              </b-form-group>
            </b-form>
            <b-button-group right>
              <b-button variant="dark" @click="$bvModal.hide('bv-modal-analytic')">Cancel</b-button>
              <b-button variant="success" :user="user" @child-props="getUser" @click="go_analytic_admin()">Validate</b-button>
            </b-button-group>
          </b-modal>

          <!-- POP-UP Planing : Create or Update -->
          <b-modal id="bv-modal-planing" titlte="Planing">
            <b-button-group align>
              <b-button variant="success" :user="user" @child-props="getUser" @click="goPlaning()">My schedule</b-button>
              <b-button variant="success" v-if="user.role_id == 1" :user="user" @child-props="getUser" @click="create_planing()">Create</b-button>
              <b-button variant="success" v-if="user.role_id == 1"  :user="user" @child-props="getUser" @click="update_planing()" >Update</b-button>
            </b-button-group>
          </b-modal>
        </b-navbar-nav>
      </b-navbar>
      <router-view :user="user" @child-props="getUser"></router-view>
    </div>
</template>

<script>
  import Vue from 'vue'
  import axios from 'axios';
  import { BootstrapVue, IconsPlugin } from 'bootstrap-vue';
  import './assets/custom.scss';
  import moment from 'moment';
  import VueCookies from "vue-cookies";

  // Install BootstrapVue
  Vue.use(BootstrapVue)
  // Optionally install the BootstrapVue icon components plugin
  Vue.use(IconsPlugin)

  const config = {
    'headers': {
      'Authorization': 'Bearer ' + VueCookies.get('token'),
    }
  };

export default {
  name: 'App',
  data(){
    return {
      show:true,
      user :{
        firstname:"",
        lastname:"",
        username:"",
        email:"",
        team_id: "",
        role_id: 0
      },
      // data for checkIn/checkout
      timeIn: "",
      timeOut: "",
      clickIn: 0,
      clickOut: 0,
      // data for edit user
      username_edit :"",
      mail_edit:"",
      pwd_edit:"",
      pwd_conf_edit:"",
      // role_id
      employe: false,
      admin:false,
      manager:false,
      //
      analytic_userID: "",
      analytic_teamID:""
    }
  },
  mounted() {
    axios.all([
      axios.get('http://13.68.159.231:4001/api/v1/profile/' + this.$router.currentRoute.params.id, config),
    ]).then(axios.spread((user) => {
      this.user = user.data.data;
    })).then(
        axios.spread((user) => {
          this.user = user.data.data;
        }));
  },
  methods:{
    // user for props
    create_planing(){
      this.$router.push({name:'createWorkingTime'});
      this.$bvModal.hide('bv-modal-planing');
    },
    update_planing(){
      this.$router.push({name:'updateWorkingTime'});
      this.$bvModal.hide('bv-modal-planing');
    },
    go_analytic_admin(){
      this.$router.push({name: 'AnalyticManager', params: {team_id:this.analytic_teamID}})
    },
    getUser: function(params) {
      this.user.firstname = params.firstname;
      this.user.lastname = params.lastname;
      this.user.username = params.username;
      this.user.email = params.email;
      this.user.role_id = params.role_id;
      this.user.team_id = params.team_id;
    },
    // Delete user
    delete_user(){
      axios.delete('http://13.68.159.231:4001/api/v1/profile/'+ this.user.id, config);
      this.$router.push('/');
    },
    //edit user
    edit_user() {
      axios.put('http://13.68.159.231:4001/api/v1/profile/' + this.user.id, {
        "user":{
          username:this.username_edit,
          email:this.mail_edit,
          password:this.pwd_edit,
          password_confirmation:this.pwd_conf_edit
        }},config);
    },
    // Sign_out : delete cookie with token and go in page HomePage
    sign_out(){
      VueCookies.remove('token');
      this.$router.push('/');
    },
    // Post clockIn
    setCheckIn(){
      this.clickIn ++;
      if (this.clickIn === 1) {
        this.timeIn = new Date();
        axios.post('http://13.68.159.231:4001/api/v1/clocks/' + this.user.id, {
          "clock": {
            time: this.timeIn,
            status: true
          }}, config);
      } else {
        document.getElementById("btn_checkIn").type = "disable";
      }
    },
    // Post ClockOut
    setCheckOut(){
      this.clickOut++;

      if (this.clickOut === 1) {
        this.timeOut = new Date();
        axios.post('http://13.68.159.231:4001/api/v1/clocks/' + this.user.id, {
          "clock": {
            time: this.timeOut,
            status: true
          }
        }, config);
      } else {
        document.getElementById("btn_checkOut").type = "disable";
      }
    },
    // Formatting the date
    parseDate(date) {
      return moment(date).format();
    },
    goTeamPage(team_id) {
      this.$router.push('/team/' + team_id)
    },
    createUser() {
      this.$router.push('/createUser/')
    },
    goHome() {
      this.$router.push('/user/' + this.user.id)
    },
    goTeams() {
      this.$router.push('/teams/')
    },
    goPlaning() {
      this.$router.push('/workingTimes/' + this.user.id)
    },
    goUsers () {
      this.$router.push('/users/')
    }
  }
};
</script>

<style lang="scss" module>
h1{
  font-size: 2px;
}
</style>