<template>
    <b-container fluid style="margin: 20px; width: 400px;">
      <b-card title="Change Role User">
        <b-card-text>
          <b-form-group>
            <b-form-checkbox-group  stacked id="checkbox-group-2" v-model="u_role_id" name="flavour-2">
              <b-form-checkbox value="1">Administrator</b-form-checkbox>
              <b-form-checkbox value="2">Manager</b-form-checkbox>
              <b-form-checkbox value="3">Employee</b-form-checkbox>
            </b-form-checkbox-group>
          </b-form-group>
          <b-button-group>
            <b-button variant="dark" squared :user="user" @child-props="getUser"  @click="go_home()">
              <b-icon icon="x-circle"></b-icon>
              Cancel
            </b-button>
            <b-button squared style="background-color: #e85d04; border-color:#e85d04" :user="user" @child-props="getUser" @click="changeRole()">
              <b-icon icon="pencil-square"></b-icon>
              Change role
            </b-button>
          </b-button-group>
        </b-card-text>
      </b-card>
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
    changeRole() {
      axios.put('http://localhost:4001/api/admin/users/' + this.$router.currentRoute.params.member_id, {
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