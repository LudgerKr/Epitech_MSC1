<template>
    <b-container fluid class="bv-example-row bv-example-row-flex-cols">
      <b-col v-for="(user, index) in users" :key="index" cols="4">
        <h2>{{ user.lastname }} {{ user.firstname }}</h2>
        <p>{{ user.username }} <br>
        {{ user.email }} <br>
        Role id: {{ user.role_id }}<br>
        Team id: {{ user.team_id }}</p>
        <b-button squared style="background-color: #e85d04; border-color:#e85d04" :user="user" @child-props="getUser" @click="changeTeam(user.id)">
          <b-icon icon="plus-square-fill"></b-icon>
          Add team
        </b-button>
      </b-col>
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
      axios.get('http://localhost:4001/api/admin/users', config),
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
      axios.put('http://localhost:4001/api/manager/users/' + user_id, {
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
h2{
  font-size: 18px;
  color: #d00000;
}

</style>