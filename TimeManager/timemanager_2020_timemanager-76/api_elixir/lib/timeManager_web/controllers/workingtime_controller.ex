defmodule TimeManagerWeb.WorkingtimeController do
  use TimeManagerWeb, :controller

  alias TimeManager.Workingtimes
  alias TimeManager.Users

  alias TimeManager.Workingtimes.Workingtime
  alias TimeManager.Users.User

  action_fallback TimeManagerWeb.FallbackController

  def index(conn, _params) do
    workingtimes = Workingtimes.list_workingtimes()
    render(conn, "index.json", workingtimes: workingtimes)
  end

  def create(conn, %{"workingtime" => workingtime_params}) do
    with {:ok, %Workingtime{} = workingtime} <- Workingtimes.create_workingtime(workingtime_params) do
      conn
      |> put_status(:created)
      |> put_resp_header("location", Routes.workingtime_path(conn, :show, workingtime))
      |> render("show.json", workingtime: workingtime)
    end
  end

  def show(conn, %{"id" => id, "start" => startTime, "end" => endTime}) do
    workingtimes = Workingtimes.get_workingtime_startDate_endDate(id, startTime, endTime)
    render(conn, "index.json", workingtimes: workingtimes)
  end

  def show(conn, %{"id" => id, "start" => startTime}) do
    workingtimes = Workingtimes.get_workingtime_startDate(id, startTime)
    render(conn, "index.json", workingtimes: workingtimes)
  end

  def show(conn, %{"id" => id, "end" => endTime}) do
    workingtimes = Workingtimes.get_workingtime_endDate(id, endTime)
    render(conn, "index.json", workingtimes: workingtimes)
  end

  def show(conn, %{"id" => userID}) do
    workingtimes = Workingtimes.get_user_workingtime(userID)
    render(conn, "index.json", workingtimes: workingtimes)
  end

  def show_workingtimesWithUserId(conn, %{"user_id" => user_id}) do
    workingtimes = Workingtimes.get_workingtimesWithUserId(user_id)
    render(conn, "index.json", workingtimes: workingtimes)
  end

  def show_workingtimeWithUserId(conn, %{"user_id" => user_id, "workingtime_id" => workingtime_id}) do
    workingtimes = Workingtimes.get_workingtimeWithUserId(user_id, workingtime_id)
    render(conn, "index.json", workingtimes: workingtimes)
  end

  def update(conn, %{"id" => id, "workingtime" => workingtime_params}) do
    workingtime = Workingtimes.get_workingtime!(id)

    with {:ok, %Workingtime{} = workingtime} <- Workingtimes.update_workingtime(workingtime, workingtime_params) do
      render(conn, "show.json", workingtime: workingtime)
    end
  end

  def delete(conn, %{"id" => id}) do
    workingtime = Workingtimes.get_workingtime!(id)

    with {:ok, %Workingtime{}} <- Workingtimes.delete_workingtime(workingtime) do
      send_resp(conn, :no_content, "")
    end
  end

end
