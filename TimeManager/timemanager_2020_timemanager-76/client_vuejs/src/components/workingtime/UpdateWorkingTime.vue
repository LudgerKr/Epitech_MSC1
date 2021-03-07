<!--Fromulaire pour update un WorkingTime-->

<template>
  <div>
    <form>
      <p>
        <label for="workingTimeStart">Time Start : </label>
        <input type="text" id="workingTimeStart" name="workingTimeStart" v-model="workingTime.start">
      </p>
      <p>
        <label for="workingTimeEnd" >Time End :</label>
        <input type="text" id="workingTimeEnd" name="workingTimeEnd" v-model="workingTime.end">
      </p>

    <!-- -->
    <v-btn class="ma-2" dark  :user="user" @child-props="getUser" @click="$router.push('WorkingTimes/' + this.user.id)">
      <v-icon dark left >
        mdi-minus-circle
      </v-icon>Cancel
    </v-btn>
      <v-btn class="ma-2" color="primary" :user="user" @child-props="getUser"  dark @click="edit(workingTime.id)">
        Update
        <v-icon dark right >
          mdi-checkbox-marked-circle
        </v-icon>
      </v-btn>
    </form>
  </div>
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
  name: "updateWorkingTime",
  data () {
    return {
      workingTime: null,
      user :{
        firstname:"",
        lastname:"",
        username:"",
        email:"",
        team_id: "",
        role_id: 0
      },
    }
  },
  mounted() {
    axios.all([
      axios.get('http://localhost:4001/api/manager/workingtimes/' + this.$router.currentRoute.params.workingtime_id, config),
    ]).then(axios.spread((workingTime) => {
      this.workingTime = workingTime.data.data;
    }));
  },
  methods: {
    edit() {
      axios.put('http://localhost:4001/api/admin/workingtimes/'  + this.$router.currentRoute.params.workingtime_id, {
        "workingtime": {
          user_id: this.$router.currentRoute.params.user_id,
          start: this.workingTime.start+".021+05:30",
          end: this.workingTime.end+".021+05:30"
        },
      }, config)
      this.$router.push('/workingTimes/' + this.$router.currentRoute.params.user_id)
    },
    getUser: function(params) {
      this.user.firstname = params.firstname;
      this.user.lastname = params.lastname;
      this.user.username = params.username;
      this.user.email = params.email;
      this.user.role_id = params.role_id;
      this.user.team_id = params.team_id;
    },
  }
};
</script>

<style scoped>
form {
  background-color:#FAFAFA;
  border:1px solid darkblue;
  border-radius:5px;
  padding:10px;
  width: 300px;
  display: block;
  margin-left: auto;
  margin-right: auto;
}
label {
  margin-top:10px;
  display:block;
}
label.inline {
  display:inline;
  margin-right:50px;
}
input{
  background-color: lightblue;
  padding:3px;
  border:1px solid darkblue;
  border-radius:5px;
  width:200px;
  box-shadow:1px 1px 2px #C0C0C0 inset;
}
button{
  background-color: lightblue;
  padding:3px;
  border:1px solid darkblue;
  border-radius:5px;
  width:200px;
  box-shadow:1px 1px 2px #C0C0C0 inset;
  width:100px;
  margin-left:5px;
  box-shadow:1px 1px 1px lightblue;
  cursor:pointer;
  margin: 2%;
}
</style>