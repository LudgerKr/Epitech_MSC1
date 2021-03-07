<template>
  <b-container fluid class="bv-example-row bv-example-row-flex-cols">
    <b-row style="margin:10px">
      <h1> Clocks </h1>
      <router-link :to="{ name: 'clocksCreate', params: {user_id: this.$router.currentRoute.params.user_id} }">
        <b-button squared style="background-color: #d00000; border-color:#d00000" :user="user" @child-props="getUser">
          <b-icon icon="plus-square-fill"></b-icon>
          <!--Add Clock-->
        </b-button>
      </router-link>
    </b-row>

      <v-col v-for="(clock, index) in clocks" :key="index" cols="3">
        <p>{{ clock.time }}</p>
        <p>{{ clock.status }}</p>
        <router-link :to="{ name: 'clocksEdit', params: {clock_id: clock.id} }">
          <b-button :user="user" @child-props="getUser">
            Change Clock
          </b-button>
        </router-link>
      </v-col>
      <b-col cols="8">
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
  name: "clocksUser",
  props: ['user'],
  data() {
    return {
      loading: true,
      errored: false,
      clocks: null,
    }
  },
  mounted () {
    axios.all([
      axios.get('http://localhost:4001/api/manager/clocks/users/' + this.$router.currentRoute.params.user_id, config),
    ]).then(axios.spread((clocks) => {
      this.clocks = clocks.data.data
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
  color: #9d0208;
}

</style>