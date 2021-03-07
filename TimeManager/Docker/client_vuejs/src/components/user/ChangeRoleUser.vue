<template>
    <b-container fluid class="bv-example-row bv-example-row-flex-cols">
      <v-row>
        <v-card-text>
          <b-col md="6">
            <b-card-body title="Change role user" style="color: orange">
              <b-form-group>
                <b-form-checkbox-group id="checkbox-group-2" v-model="u_role_id" name="flavour-2">
                  <b-form-checkbox value="1">Administrator</b-form-checkbox>
                  <b-form-checkbox value="2">Manager</b-form-checkbox>
                  <b-form-checkbox value="3">Employee</b-form-checkbox>
                </b-form-checkbox-group>
              </b-form-group>
            </b-card-body>
            <b-button style="margin: 0 auto; display: block; background-color: orange;" :user="user" @child-props="getUser" @click="changeRole()">Change role</b-button>
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
  name: "changeRoleUser",
  props: ['user'],
  data() {
    return {
      loading: true,
      errored: false,
      u_role_id: "",
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
    changeRole() {
      axios.put('http://13.68.159.231:4001/api/admin/users/' + this.$router.currentRoute.params.member_id, {
        "user": {
          role_id: this.u_role_id[0],
        }
      }, config)
      this.$router.push('/users/')
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