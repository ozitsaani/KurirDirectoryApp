package com.latihan.kurirdirectoryapp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UserFilteringAndParsingTest {

    private val jsonSample = """
        [
          {
            "id": 1,
            "name": "Leanne Graham",
            "username": "Bret",
            "email": "Sincere@april.biz",
            "phone": "1-770-736-8031 x56442",
            "company": {
              "name": "Romaguera-Crona",
              "catchPhrase": "Multi-layered client-server neural-net",
              "bs": "harness real-time e-markets"
            }
          },
          {
            "id": 2,
            "name": "Ervin Howell",
            "username": "Antonette",
            "email": "Shanna@melissa.tv",
            "phone": "010-692-6593 x09125",
            "company": {
              "name": "Deckow-Crist",
              "catchPhrase": "Proactive didactic contingency",
              "bs": "synergize scalable supply-chains"
            }
          }
        ]
    """.trimIndent()

    @Test
    fun testJsonParsingMatchesModel() {
        val type = object : TypeToken<List<UserResponse>>() {}.type
        val users: List<UserResponse> = Gson().fromJson(jsonSample, type)

        assertEquals(2, users.size)
        assertEquals("Leanne Graham", users[0].name)
        assertEquals("Romaguera-Crona", users[0].company.companyName)
        assertEquals("Ervin Howell", users[1].name)
        assertEquals("Deckow-Crist", users[1].company.companyName)
    }

    @Test
    fun testFilterByNameCaseInsensitive() {
        val type = object : TypeToken<List<UserResponse>>() {}.type
        val users: List<UserResponse> = Gson().fromJson(jsonSample, type)

        val keyword = "ervin"
        val filtered = users.filter { pengguna ->
            pengguna.name.contains(keyword, ignoreCase = true) ||
            pengguna.company.companyName.contains(keyword, ignoreCase = true)
        }

        assertEquals(1, filtered.size)
        assertEquals("Ervin Howell", filtered[0].name)
    }

    @Test
    fun testFilterByCompanyCaseInsensitive() {
        val type = object : TypeToken<List<UserResponse>>() {}.type
        val users: List<UserResponse> = Gson().fromJson(jsonSample, type)

        val keyword = "romaguera"
        val filtered = users.filter { pengguna ->
            pengguna.name.contains(keyword, ignoreCase = true) ||
            pengguna.company.companyName.contains(keyword, ignoreCase = true)
        }

        assertEquals(1, filtered.size)
        assertEquals("Leanne Graham", filtered[0].name)
    }

    @Test
    fun testFilterEmptyStateWhenNoMatch() {
        val type = object : TypeToken<List<UserResponse>>() {}.type
        val users: List<UserResponse> = Gson().fromJson(jsonSample, type)

        val keyword = "nonexistent"
        val filtered = users.filter { pengguna ->
            pengguna.name.contains(keyword, ignoreCase = true) ||
            pengguna.company.companyName.contains(keyword, ignoreCase = true)
        }

        assertTrue(filtered.isEmpty())
    }
}
