<template>
  <div>
      <h1>WorkingTimes</h1>
      <b-col v-for="(workingTime, index) in workingTimes" :key="index">
        <p>{{ parseDate(workingTime.start) }} {{ parseDate(workingTime.end) }}</p>
        <router-link :user="user" @child-props="getUser" :to="{ name: 'updateWorkingTime', params:
        {user_id: workingTime.user_id, workingtime_id: workingTime.id} }">
          <b-button variant="success" >
            <b-icon icon="check"></b-icon>
            Edit
          </b-button>
        </router-link>
        <router-link :to="{ name: 'WorkingTimes' }">
          <b-button variant="danger" :user="user" @child-props="getUser" @click="supress(workingTime.id)">
            <b-icon icon="trash"></b-icon>
            Delete
          </b-button>
        </router-link>
      </b-col>
  </div>
</template>

<script>

import axios from 'axios';
import moment from "moment";
import VueCookies from "vue-cookies";

const config = {
  'headers': {
    'Authorization': 'Bearer ' + VueCookies.get('token'),
  }
};

export default {
  name: "WorkingTimes",
  data () {
    return {
      workingTimes: null,
      loading: true,
      errored: false,
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
  mounted() {
    axios.all([
      axios.get('http://localhost:4001/api/v1/workingtimes/' + this.$router.currentRoute.params.user_id, config),
    ]).then(axios.spread((workingTimes) => {
      this.workingTimes = workingTimes.data.data;
    }));
  },
  methods: {
    supress(workingtime_id) {
      axios.delete('http://localhost:4001/api/admin/workingtimes/' + workingtime_id, config);
    },
    parseDate(date) {
      return moment(date).format("MMMM Do YYYY, h:mm a");
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
}
</script>

<style scoped>
h1{
  margin: 10px;
  font-size: 30px;
  color: #e85d04;
}
</style>