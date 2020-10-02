package com.islam.hesn.myapplication.search.model

import android.os.Parcelable
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

/**
 * T : Aya or bible verse
 */

class Section<T : Parcelable>(title: String?, items: List<T>) :
    ExpandableGroup<T>(title, items)