<template>
    <b-container fluid class="bv-example-row bv-example-row-flex-cols">
      <b-row>
        <router-link :to="{ name: 'createTeam' }">
          <b-button v-if="role_id==1" :user="user" @child-props="getUser">New Team</b-button>
        </router-link>
        <br>
        <br>
        <v-col v-for="(team, index) in teams" :key="index" cols="3">
          <b-img :src="require('../../assets/user.jpg')" style="height: 25% "> </b-img>
          <h3>{{team.name}}</h3>
        </v-col>
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
      axios.get('http://13.68.159.231:4001/api/admin/teams/', config),
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