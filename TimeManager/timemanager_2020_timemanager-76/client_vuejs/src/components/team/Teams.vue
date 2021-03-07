<template>
    <b-container fluid class="bv-example-row bv-example-row-flex-cols">
      <b-row style="margin: 10px">
        <h1> All my teams </h1>
        <router-link :to="{ name: 'createTeam' }">
          <b-button squared style="background-color: #d00000; border-color:#d00000;" v-if="role_id==1" :user="user" @child-props="getUser">
            <b-icon icon="plus-square-fill"></b-icon>
            <!--New Team-->
          </b-button>
        </router-link>
      </b-row>
      <b-row>
        <b-col v-for="(team, index) in teams" :key="index" cols="3">
          <b-img :src="require('../../assets/user.jpg')" style="height: 25% "> </b-img>
          <h3><br>{{team.name}}</h3>
        </b-col>
        <b-col cols="8">
        </b-col>
      </b-row>

    </b-container>
</template>

<script>

import axios from 'axios';
import VueCookies from "vue-cookies";

const config = {
  'headers': {
    'Authorization': 'Bearer ' + VueCookies.get('token'),
  }
};


export default {
  name: "teamHome",
  props:['user'],
  data () {
    return {
      teams: null,
      loading: true,
      errored: false,
      role_id: VueCookies.get("role")
    }
  },

  mounted () {
    axios.all([
      axios.get('http://localhost:4001/api/admin/teams/', config),
    ]).then(axios.spread((teams) => {
      this.teams = teams.data.data
    }));
  },
  methods: {
    getUser: function(params) {
      this.user.firstname = params.firstname;
      this.user.lastname = params.lastname;
      this.user.username = params.username;
      this.user.email = params.email;
      this.user.role_id = params.role_id;
    },
  }
}
</script>

<style scoped>
h1{
  margin: 10px;
  font-size: 20px;
}

</style>