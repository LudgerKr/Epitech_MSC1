defmodule TimeManager.Clocks do
  @moduledoc """
  The Clocks context.
  """

  import Ecto.Query, warn: false
  alias TimeManager.Repo

  alias TimeManager.Clocks.Clock

  @doc """
  Returns the list of clocks.

  ## Examples

      iex> list_clocks()
      [%Clock{}, ...]

  """
  def list_clocks do
    Repo.all(Clock)
  end

  @doc """
  Gets a single clock.

  Raises `Ecto.NoResultsError` if the Clock does not exist.

  ## Examples

      iex> get_clock!(123)
      %Clock{}

      iex> get_clock!(456)
      ** (Ecto.NoResultsError)

  """
  def get_clock!(id), do: Repo.get!(Clock, id)
  
  def get_AllUserId(id) do
    Clock
    |> where(user_id: ^id)
    |> Repo.all()
  end

  def get_clock_per_day(id, start_time, end_time) do
    Clock
    |> where([c], c.user_id == ^id and c.time >= ^start_time and c.time <= ^end_time)
    |> order_by([c], c.id)
    |> Repo.all()
  end

  @doc """
  Creates a clock.

  ## Examples

      iex> create_clock(%{field: value})
      {:ok, %Clock{}}

      iex> create_clock(%{field: bad_value})
      {:error, %Ecto.Changeset{}}

  """
  def create_clock(attrs \\ %{}) do
    %Clock{}
    |> Clock.changeset(attrs)
    |> Repo.insert()
  end

  def create_clockWithoutUserId(attrs \\ %{}, id) do
    %Clock{}
    |> Clock.changeset(attrs)
    |> Clock.changeset(%{user_id: id})
    |> Repo.insert()
  end


  @doc """
  Updates a clock.

  ## Examples

      iex> update_clock(clock, %{field: new_value})
      {:ok, %Clock{}}

      iex> update_clock(clock, %{field: bad_value})
      {:error, %Ecto.Changeset{}}

  """
  def update_clock(%Clock{} = clock, attrs) do
    clock
    |> Clock.changeset(attrs)
    |> Repo.update()
  end

  @doc """
  Deletes a clock.

  ## Examples

      iex> delete_clock(clock)
      {:ok, %Clock{}}

      iex> delete_clock(clock)
      {:error, %Ecto.Changeset{}}

  """
  def delete_clock(%Clock{} = clock) do
    Repo.delete(clock)
  end

  @doc """
  Returns an `%Ecto.Changeset{}` for tracking clock changes.

  ## Examples

      iex> change_clock(clock)
      %Ecto.Changeset{data: %Clock{}}

  """
  def change_clock(%Clock{} = clock, attrs \\ %{}) do
    Clock.changeset(clock, attrs)
  end

  def clock_count(user_id) do
    query = from clock in Clock,
                 where: clock.user_id == ^user_id

    Repo.aggregate(query, :count)
  end
end
