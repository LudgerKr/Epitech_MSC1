<template>
  <b-container fluid class="bv-example-row bv-example-row-flex-cols">
    <b-form style="width: 500px">
      <h1> Change team's name</h1>
      <b-form-group>
        <b-form-input class="input_signup" type="text" placeholder="Name" v-model="team.name"></b-form-input>
      </b-form-group>
      <b-form-group>
          <b-button-group>
            <b-button variant="dark" squared :user="user" @child-props="getUser"  @click="go_home()">
              <b-icon icon="x-circle"></b-icon>
              Cancel
            </b-button>
            <router-link :to="{ name: 'teamHome', params: {id: this.user_id} }">
              <b-button squared style="background-color: #e85d04; border-color:#e85d04" :user="user" @child-props="getUser" @click="edit()">
                <b-icon icon="check"></b-icon>
                Edit
              </b-button>
            </router-link>
          </b-button-group>
      </b-form-group>
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
      axios.get('http://localhost:4001/api/manager/teams/' + this.$router.currentRoute.params.team_id, config),
    ]).then(axios.spread((team) => {
      this.team = team.data.data;
    }));
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
    edit() {
      axios.put('http://localhost:4001/api/manager/teams/' + this.team.id, {
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
  margin: 20px;
  font-size: 30px;
  color: #e85d04;
}
</style>