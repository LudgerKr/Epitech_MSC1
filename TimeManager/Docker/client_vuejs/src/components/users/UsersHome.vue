<template>
    <b-container fluid class="bv-example-row bv-example-row-flex-cols">
      <b-row>
        <v-col v-for="(member, index) in users" :key="index" cols="3">
          <b-img :src="require('../../assets/user.jpg')" style="height: 25% "> </b-img>
          <h3>{{member.lastname}} {{member.firstname}}</h3>
          <p>{{member.email}}</p>
          <p>Role : {{member.role_id}}</p>
          <p>Team : {{member.team_id}}</p>
          <router-link :to="{ name: 'changeRoleUser', params: {member_id: member.id} }">
            <b-button v-if="role_id==1"  :user="user" @child-props="getUser" variant="success">Change Role</b-button>
          </router-link>
          <router-link :to="{ name: 'clocksUser', params: {user_id: member.id} }">
            <b-button v-if="role_id==1"  :user="user" @child-props="getUser">Clocks</b-button>
          </router-link>
          <router-link :to="{ name: 'WorkingTimes', params: {user_id: member.id} }">
            <b-button v-if="role_id==1"  :user="user" @child-props="getUser">Workingtimes</b-button>
          </router-link>
          <router-link :to="{ name: 'AnalyticUser', params: {id: member.id} }">
            <b-button v-if="role_id==1"  :user="user" @child-props="getUser">Analytics</b-button>
          </router-link>
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
  name: "usersHome",
  props:['user'],
  data () {
    return {
      users: null,
      team: null,
      loading: true,
      errored: false,
      role_id: VueCookies.get("role")
    }
  },

  mounted () {
    axios.all([
      axios.get('http://13.68.159.231:4001/api/admin/users/', config),
    ]).then(axios.spread((users) => {
      this.users = users.data.data
    }));

    axios.all([
      axios.get('http://13.68.159.231:4001/api/manager/teams/' + this.$router.currentRoute.params.team_id, config),
    ]).then(axios.spread((team) => {
      this.team = team.data.data
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