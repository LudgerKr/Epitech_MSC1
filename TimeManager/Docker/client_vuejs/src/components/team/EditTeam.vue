<template>
  <b-container fluid class="bv-example-row bv-example-row-flex-cols">
    <v-row>
      <v-card-text>
        <b-col md="6">
          <b-card-body title="Change team's name" style="color: orange">
            <b-form-input class="input_signup" type="text" placeholder="Name" v-model="team.name"></b-form-input>
          </b-card-body>
          <router-link :to="{ name: 'teamHome', params: {id: this.user_id} }">
            <b-button style="margin: 0 auto; display: block; background-color: orange;" :user="user" @child-props="getUser" @click="edit()">Edit</b-button>
          </router-link>
        </b-col>
      </v-card-text>
    </v-row>
  </b-container>
</template>

<script>

import axios from "axios";
import VueCookies from "vue-cookies";

const config = {
  'headers': {
    'Authorization': 'Bearer ' + VueCookies.get('token'),
  }
};

export default {
  name: "editTeam",
  props: ['user'],
  data() {
    return {
      loading: true,
      errored: false,
      team: null,
    }
  },
  mounted() {
    axios.all([
      axios.get('http://13.68.159.231:4001/api/manager/teams/' + this.$router.currentRoute.params.team_id, config),
    ]).then(axios.spread((team) => {
      this.team = team.data.data;
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
    edit() {
      axios.put('http://13.68.159.231:4001/api/manager/teams/' + this.team.id, {
        "team": {
          name: this.team.name,
        }
      }, config)
    }
  }
}

</script>

<style scoped>
h1{
  margin: 10px;
  font-size: 20px;
}

</style>