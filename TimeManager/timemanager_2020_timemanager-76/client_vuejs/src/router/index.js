/* Fichiers contenant toutes les routes */

import Vue from 'vue'
import VueRouter from 'vue-router'

import HomePage from "../views/HomePage";
import UserHome from "@/components/user/UserHome";
import CreateUser from "@/components/user/CreateUser";
import ChangeRoleUser from "@/components/user/ChangeRoleUser";

import TeamHome from "@/components/team/TeamHome";
import Teams from "@/components/team/Teams";
import EditTeam from "@/components/team/EditTeam";
import CreateTeam from "@/components/team/CreateTeam";
import AddMemberTeam from "@/components/team/AddMemberTeam";

import ClocksUser from "@/components/clock/Clocks";
import ClocksEdit from "@/components/clock/EditClock";
import ClocksCreate from "@/components/clock/CreateClock";

import AnalyticUser from "@/components/vueMorris/AnalyticUser";
import AnalyticManager from "@/components/vueMorris/AnalyticManager";
import AnalyticAdmin from "@/components/vueMorris/AnalyticAdmin";

import CreateWorkingTime from "@/components/workingtime/CreateWorkingTime";
import UpdateWorkingTime from "@/components/workingtime/UpdateWorkingTime";
import WorkingTimes from "@/components/workingtime/WorkingTimes";


import UsersHome from "@/components/users/UsersHome";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: HomePage
  },
  {
    path: '/user/:id',
    name: 'userHome',
    component: UserHome
  },
  {
    path: '/createUser',
    name: 'createUser',
    component: CreateUser
  },
  {
    path: '/team/:team_id',
    name: 'teamHome',
    component: TeamHome
  },
  {
    path: '/teams',
    name: 'teams',
    component: Teams
  },
  {
    path: '/changeRole/:member_id',
    name: 'changeRoleUser',
    component: ChangeRoleUser
  },
  {
    path: '/team/edit/:team_id',
    name: 'editTeam',
    component: EditTeam
  },
  {
    path: '/team/create/:user_id',
    name: 'createTeam',
    component: CreateTeam
  },
  {
    path: '/team/newMember/:team_id',
    name: 'addMemberTeam',
    component: AddMemberTeam
  },
  {
    path: '/clocksUser/:user_id',
    name: 'clocksUser',
    component: ClocksUser
  },
  {
    path: '/clocksUser/edit/:clock_id',
    name: 'clocksEdit',
    component: ClocksEdit
  },
  {
    path: '/clocksUser/create/:user_id',
    name: 'clocksCreate',
    component: ClocksCreate
  },
  {
    path: '/AnalyticUser/:id',
    name: 'AnalyticUser',
    component: AnalyticUser
  },
  {
    path: '/AnalyticManager/:team_id',
    name: 'AnalyticManager',
    component: AnalyticManager
  },
  {
    path: '/AnalyticAdmin/:id/:team_id',
    name: 'AnalyticAdmin',
    component: AnalyticAdmin
  },
  {
    path: '/workingTimes/:user_id',
    name:'WorkingTimes',
    component: WorkingTimes
  },
  {
    path: '/createWorkingTime',
    name: 'createWorkingTime',
    component: CreateWorkingTime
  },
  {
    path: '/updateWorkingTime/:user_id/:workingtime_id',
    name: 'updateWorkingTime',
    component: UpdateWorkingTime
  },
  {
    path: '/users/',
    name: 'usersHome',
    component: UsersHome
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
