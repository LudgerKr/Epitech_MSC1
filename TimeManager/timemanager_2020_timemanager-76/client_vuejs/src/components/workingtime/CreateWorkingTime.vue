<!--Fromulaire pour creer un WorkingTime-->
<template>
  <b-container fluid>
    <b-form>
      <h1> Create a workingtime </h1>
      <b-form style="width: 500px">
        <b-form-group label="User ID" label-for="workingTimeUserId">
          <b-form-input id="workingTimeUserId" type="text" v-model="workingTimeUserId"></b-form-input>
        </b-form-group>
        <b-form-group label="Time Start" label-for="workingTimeStart">
          <b-datepicker  v-model="startDate" locale="fr"></b-datepicker>
          <b-timepicker  v-model="startTime" locale="fr"></b-timepicker>
        </b-form-group>
        <b-form-group label="Time End" label-for="workingTimeEnd">
          <b-datepicker  v-model="endDate" locale="fr"></b-datepicker>
          <b-timepicker   v-model="endTime" locale="fr"></b-timepicker>
        </b-form-group>
        <b-button-group right>
            <b-button variant="dark" squared :user="user" @child-props="getUser"  @click="go_home()">
              <b-icon icon="x-circle"></b-icon>
              Cancel
            </b-button>
          <router-link :to="{ name: 'WorkingTimes', params: {user_id: workingTimeUserId} }">
            <b-button squared style="background-color: #e85d04; border-color:#e85d04" :user="user" @child-props="getUser" @click="create()" >
              <b-icon icon="plus-square-fill"></b-icon>
              Accept
            </b-button>
          </router-link>
        </b-button-group>
      </b-form>
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
      token:"",
      token_decode:"",
      token_id:"",

    }
  },
  methods: {
    create() {
      axios.post('http://localhost:4001/api/admin/workingtimes', {
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
      this.$router.push('/user/'+this.token_id);
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
h1{
  margin: 10px;
  font-size: 20px;
  color: #e85d04;
}
</style>