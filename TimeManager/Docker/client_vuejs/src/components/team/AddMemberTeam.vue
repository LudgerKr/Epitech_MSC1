<template>
    <b-container fluid class="bv-example-row bv-example-row-flex-cols">
      <v-col v-for="(user, index) in users" :key="index" cols="4">
        <h4>{{ user.lastname }} {{ user.firstname }}</h4>
        <p>{{ user.username }}</p>
        <p>{{ user.email }}</p>
        <p>Role id: {{ user.role_id }}</p>
        <p>Team id: {{ user.team_id }}</p>
        <b-button style="display: block; background-color: orange;" :user="user" @child-props="getUser" @click="changeTeam(user.id)">Add team</b-button>
      </v-col>
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
  name: "addMemberTeam",
  props: ['user'],
  data() {
    return {
      loading: true,
      errored: false,
      users: null,
    }
  },
  mounted () {
    axios.all([
      axios.get('http://13.68.159.231:4001/api/admin/users', config),
    ]).then(axios.spread((users) => {
      this.users = users.data.data
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
    changeTeam(user_id) {
      axios.put('http://13.68.159.231:4001/api/manager/users/' + user_id, {
        "user": {
          team_id: this.$router.currentRoute.params.team_id,
        }
      }, config)
      this.$router.push('/team/' + this.user.team_id)
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