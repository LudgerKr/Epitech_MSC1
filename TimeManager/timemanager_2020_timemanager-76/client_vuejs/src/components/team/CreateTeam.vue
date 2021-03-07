<template>
  <b-container fluid class="bv-example-row bv-example-row-flex-cols">
    <b-form style="width: 500px">
      <h1>Create a new team</h1>
      <b-form-group>
        <b-form-input class="input_signup" type="text" placeholder="Name" v-model="name"></b-form-input>
      </b-form-group>
      <b-button-group>
        <b-button variant="dark" squared :user="user" @child-props="getUser"  @click="go_home()">
          <b-icon icon="x-circle"></b-icon>
          Cancel
        </b-button>
        <b-button squared style="background-color: #e85d04; border-color:#e85d04" :user="user" @child-props="getUser" @click="createTeam()">
          <b-icon icon="check"></b-icon>
          Create
        </b-button>
      </b-button-group>
    </b-form>
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
    go_home(){
      this.$router.push('/team/'+this.user.team_id);
    },
    getUser: function(params) {
      this.user.firstname = params.firstname;
      this.user.lastname = params.lastname;
      this.user.username = params.username;
      this.user.email = params.email;
      this.user.role_id = params.role_id;
    },
    createTeam() {
      axios.post('http://localhost:4001/api/admin/teams', {
        "team": {
          name: this.name
        }
      }, config)
      this.$router.push('/teams/');
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