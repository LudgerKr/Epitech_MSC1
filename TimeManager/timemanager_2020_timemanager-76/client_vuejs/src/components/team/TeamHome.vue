<template>
    <b-container fluid class="bv-example-row bv-example-row-flex-cols">
      <b-row >
          <h1>{{team.name}}</h1>
          <b-button-group style="margin: 10px">
            <router-link :to="{ name: 'addMemberTeam', params: {team_id: team.id} }">
              <b-button squared style="background-color: #d00000; border-color:#d00000" :user="user" @child-props="getUser">
                <b-icon icon="plus-square-fill"></b-icon>
                <!--Add new member-->
              </b-button>
            </router-link>
            <router-link :to="{ name: 'editTeam', params: {team_id: team.id} }">
              <b-button squared style="background-color: #e85d04; border-color:#e85d04" :user="user" @child-props="getUser">
                <b-icon icon="pencil-square"></b-icon>
                <!--Change Team's name-->
              </b-button>
            </router-link>
          </b-button-group>
      </b-row>

      <b-row>
        <b-col v-for="(member, index) in users" :key="index" cols="3">
         <b-img :src="require('../../assets/user.jpg')" style="height: 25% "> </b-img>
          <h2>{{member.lastname}} {{member.firstname}} <br></h2>
          <p>{{member.email}} <br>
          Role : {{member.role_id}}<br>
          Team : {{member.team_id}}<br>
          </p>

          <b-button-group right>
            <router-link :to="{ name: 'changeRoleUser', params: {member_id: member.id} }">
              <b-button squared style="background-color: #e85d04; border-color:#e85d04" v-if="role_id==1"  :user="user" @child-props="getUser" variant="success">
                <b-icon icon="pencil-square"></b-icon>
                <!--Change Role-->
              </b-button>
            </router-link>
            <router-link :to="{ name: 'clocksUser', params: {user_id: member.id} }">
              <b-button style="background-color: #f48c06; border-color:#f48c06" squared v-if="role_id!=3"  :user="user" @child-props="getUser">
                <b-icon icon="clock"></b-icon>
                <!-- Clocks -->
              </b-button>
            </router-link>
            <router-link :to="{ name: 'WorkingTimes', params: {user_id: member.id} }">
              <b-button squared style="background-color: #faa307; border-color:#faa307" v-if="role_id!=3"  :user="user" @child-props="getUser">
                <b-icon icon="briefcase-fill"></b-icon>
                <!--Workingtimes-->
              </b-button>
            </router-link>
            <router-link :to="{ name: 'AnalyticUser', params: {id: member.id} }">
              <b-button squared style="background-color: #ffba08; border-color:#ffba08" v-if="role_id!=3"  :user="user" @child-props="getUser">
                <b-icon icon="bar-chart-fill"></b-icon>
                <!-- Analytics -->
              </b-button>
            </router-link>
          </b-button-group>
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
      users: null,
      team: null,
      loading: true,
      errored: false,
      role_id: VueCookies.get("role")
    }
  },

  mounted () {
    axios.all([
      axios.get('http://localhost:4001/api/manager/users/teams/' + this.$router.currentRoute.params.team_id, config),
    ]).then(axios.spread((users) => {
      this.users = users.data.data
    }));

    axios.all([
      axios.get('http://localhost:4001/api/manager/teams/' + this.$router.currentRoute.params.team_id, config),
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
  font-size: 30px;
  color: #9d0208;
}
h2{
  font-size: 18px;
  color: #d00000;
}
</style>