defmodule TimeManagerWeb.ClockController do
  use TimeManagerWeb, :controller

  alias TimeManager.Clocks
  alias TimeManager.Clocks.Clock

  action_fallback TimeManagerWeb.FallbackController

  def index(conn, _params) do
    clocks = Clocks.list_clocks()
    render(conn, "index.json", clocks: clocks)
  end

  def create(conn, %{"clock" => clock_params}) do
    with {:ok, %Clock{} = clock} <- Clocks.create_clock(clock_params) do
      conn
      |> put_status(:created)
      |> put_resp_header("location", Routes.clock_path(conn, :show, clock))
      |> render("show.json", clock: clock)
    end
  end

  def create_clockWithoutUserId(conn, %{"user_id" => id, "clock" => clock_params}) do
    with {:ok, %Clock{} = clock} <- Clocks.create_clockWithoutUserId(clock_params, id) do
      conn
      |> put_status(:created)
      |> put_resp_header("location", Routes.clock_path(conn, :show, clock))
      |> render("show.json", clock: clock)
    end
  end

  # Get tous les clocks avant le "time"
  def show(conn, %{"id" => id, "start_time" => start_time, "end_time" => end_time}) do
    clocks = Clocks.get_clock_per_day(id, start_time, end_time)
    render(conn, "index.json", clocks: clocks)
  end

  def show(conn, %{"id" => id}) do
    clock = Clocks.get_clock!(id)
    render(conn, "show.json", clock: clock)
  end

  def show_usersID(conn, %{"user_id" => user_id}) do
    clocks = Clocks.get_AllUserId(user_id)
    render(conn, "index.json", clocks: clocks)
  end

  def show_count(conn, %{"user_id" => user_id}) do
    count = Clocks.clock_count(user_id)
    render(conn, "count.json", message: count)
  end

  def update(conn, %{"id" => id, "clock" => clock_params}) do
    clock = Clocks.get_clock!(id)
    with {:ok, %Clock{} = clock} <- Clocks.update_clock(clock, clock_params) do
      render(conn, "show.json", clock: clock)
    end
  end

  def delete(conn, %{"id" => id}) do
    clock = Clocks.get_clock!(id)

    with {:ok, %Clock{}} <- Clocks.delete_clock(clock) do
      send_resp(conn, :no_content, "")
    end
  end
end
