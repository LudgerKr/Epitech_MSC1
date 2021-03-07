<template>
    <b-container fluid class="bv-example-row bv-example-row-flex-cols">

      <b-row>
        <b-col cols="3" class="col_left">
          <b-img :src="require('../../assets/user.jpg')" style="height: 25% "> </b-img>
          <h1>Bonjour, {{user.lastname}}</h1>
        </b-col>
        <b-col cols="8">
          <AnalyticUser></AnalyticUser>
        </b-col>
      </b-row>
      <div>

      </div>
    </b-container>
</template>

<script>
import AnalyticUser from "@/components/vueMorris/AnalyticUser";

export default {
  name: "User",
  props:['user'],
  components:{
    AnalyticUser
  },
  data () {
    return {
      loading: true,
      errored: false
    }
  }
}
</script>

<style scoped>
h1{
  margin: 10px;
  font-size: 20px;
}
.col_left{
  height:500px;
}

</style>