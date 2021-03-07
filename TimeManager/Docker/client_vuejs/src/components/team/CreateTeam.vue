<template>
    <b-container fluid class="bv-example-row bv-example-row-flex-cols">
      <v-row>
        <v-card-text>
          <b-col md="6">
            <b-card-body title="Create a new team" style="color: orange">
              <b-form-input class="input_signup" type="text" placeholder="Name" v-model="name"></b-form-input>
            </b-card-body>
            <b-button style="margin: 0 auto; display: block; background-color: orange;" :user="user" @child-props="getUser" @click="createTeam()">Create</b-button>
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
  name: "createTeam",
  props: ['user'],
  data() {
    return {
      loading: true,
      errored: false,
      name: "",
    }
  },
  methods: {
    getUser: function(params) {
      this.user.firstname = params.firstname;
      this.user.lastname = params.lastname;
      this.user.username = params.username;
      this.user.email = params.email;
      this.user.role_id = params.role_id;
    },
    createTeam() {
      axios.post('http://13.68.159.231:4001/api/admin/teams', {
        "team": {
          name: this.name
        }
      }, config)
      this.$router.push('/teams/')
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