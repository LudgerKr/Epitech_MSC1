defmodule TimeManager.TotosTest do
  use TimeManager.DataCase

  alias TimeManager.Totos

  describe "totos" do
    alias TimeManager.Totos.Toto

    @valid_attrs %{toto: "some toto", username: "some username"}
    @update_attrs %{toto: "some updated toto", username: "some updated username"}
    @invalid_attrs %{toto: nil, username: nil}

    def toto_fixture(attrs \\ %{}) do
      {:ok, toto} =
        attrs
        |> Enum.into(@valid_attrs)
        |> Totos.create_toto()

      toto
    end

    test "list_totos/0 returns all totos" do
      toto = toto_fixture()
      assert Totos.list_totos() == [toto]
    end

    test "get_toto!/1 returns the toto with given id" do
      toto = toto_fixture()
      assert Totos.get_toto!(toto.id) == toto
    end

    test "create_toto/1 with valid data creates a toto" do
      assert {:ok, %Toto{} = toto} = Totos.create_toto(@valid_attrs)
      assert toto.toto == "some toto"
      assert toto.username == "some username"
    end

    test "create_toto/1 with invalid data returns error changeset" do
      assert {:error, %Ecto.Changeset{}} = Totos.create_toto(@invalid_attrs)
    end

    test "update_toto/2 with valid data updates the toto" do
      toto = toto_fixture()
      assert {:ok, %Toto{} = toto} = Totos.update_toto(toto, @update_attrs)
      assert toto.toto == "some updated toto"
      assert toto.username == "some updated username"
    end

    test "update_toto/2 with invalid data returns error changeset" do
      toto = toto_fixture()
      assert {:error, %Ecto.Changeset{}} = Totos.update_toto(toto, @invalid_attrs)
      assert toto == Totos.get_toto!(toto.id)
    end

    test "delete_toto/1 deletes the toto" do
      toto = toto_fixture()
      assert {:ok, %Toto{}} = Totos.delete_toto(toto)
      assert_raise Ecto.NoResultsError, fn -> Totos.get_toto!(toto.id) end
    end

    test "change_toto/1 returns a toto changeset" do
      toto = toto_fixture()
      assert %Ecto.Changeset{} = Totos.change_toto(toto)
    end
  end
end
