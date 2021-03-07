<!--Fromulaire pour creer un WorkingTime-->
<template>
  <b-container>
    <b-form>
      <b-form-group label="User ID" label-for="workingTimeUserId">
        <b-form-input id="workingTimeUserId" type="text" v-model="workingTimeUserId"></b-form-input>
      </b-form-group>
      <b-form-group label="Time Start" label-for="workingTimeStart">
        <b-datepicker style="border-color: black" v-model="startDate" locale="fr"></b-datepicker>
        <b-timepicker style="border-color: black" v-model="startTime" locale="fr"></b-timepicker>
      </b-form-group>
      <b-form-group label="Time End" label-for="workingTimeEnd">
        <b-datepicker style="border-color: black" v-model="endDate" locale="fr"></b-datepicker>
        <b-timepicker  style="border-color: black" v-model="endTime" locale="fr"></b-timepicker>
      </b-form-group>
      <b-button-group right>
        <b-button variant="dark"  :user="user" @child-props="getUser"  @click="go_home()">Cancel</b-button>
        <router-link :to="{ name: 'WorkingTimes', params: {user_id: workingTimeUserId} }">
          <b-button variant="success"   :user="user" @child-props="getUser" @click="create()" >Accept</b-button>
        </router-link>
      </b-button-group>
    </b-form>

  </b-container>
</template>

<script>
import axios from "axios";
import VueCookies from "vue-cookies";
import VueJwtDecode from "vue-jwt-decode";

const config = {
  'headers': {
    'Authorization': 'Bearer ' + VueCookies.get('token'),
  }
};

export default {
  name: "createWorkingTime",
  data () {
    return {
      startDate: "2020-11-02",
      startTime: "05:00:00",
      endDate: "2020-11-02",
      endTime: "05:00:00",
      workingTimeUserId: "",
      user :{
        firstname:"",
        lastname:"",
        username:"",
        email:"",
        team_id: "",
        role_id: 0
      },
    }
  },
  methods: {
    create() {
      axios.post('http://13.68.159.231:4001/api/admin/workingtimes', {
        "workingtime": {
          start: this.startDate+" "+this.startTime+".021+05:30",
          end: this.endDate+" "+this.endTime+".021+05:30",
          user_id: this.workingTimeUserId,
        }
      },config)

    },
    go_home(){
      this.token =VueCookies.get('token');
      this.token_decode= VueJwtDecode.decode(this.token);
      this.token_id = this.token_decode.sub;
      this.$router.push('/user/'+this.user_id);
    },
    getUser: function(params) {
      this.user.firstname = params.firstname;
      this.user.lastname = params.lastname;
      this.user.username = params.username;
      this.user.email = params.email;
      this.user.role_id = params.role_id;
      this.user.team_id = params.team_id;
    },
  }
};
</script>

<style scoped>
form {
  background-color:rgb(247,194,68);
  border:1px solid black;
  border-radius:5px;
  padding:10px;
  width: 300px;
  display: block;
  margin-left: auto;
  margin-right: auto;
}
label {
  margin-top:10px;
  display:block;
}
label.inline {
  display:inline;
  margin-right:50px;
}
input{
  background-color: white;
  padding:3px;
  border:1px solid black;
  border-radius:5px;
  width:200px;
  box-shadow:1px 1px 2px #C0C0C0 inset;
}
</style>