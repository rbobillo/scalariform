package scalariform.formatter

import scalariform.formatter.preferences._

// format: OFF
class FirstArgumentOnNewlineFormatterTest extends AbstractExpressionFormatterTest {
  {
  implicit val formattingPreferences = FormattingPreferences.setPreference(FirstArgumentOnNewline, Prevent)

  """foo(a,
    |    b)""" ==>
  """foo(a,
    |  b)"""

  """foo(a = 1,
    |    bb=2)""" ==>
  """foo(a = 1,
    |  bb = 2)"""
  }

  {
  implicit val formattingPreferences = FormattingPreferences.setPreference(FirstArgumentOnNewline, Preserve)

  """foo(a,
    |    b)""" ==>
  """foo(a,
    |  b)"""

  """foo(
    |  a,
    |    b)""" ==>
  """foo(
    |  a,
    |  b)"""

  """foo(a = 1,
    |    bb = 2)""" ==>
  """foo(a = 1,
    |  bb = 2)"""
  }

  {
  implicit val formattingPreferences = FormattingPreferences.setPreference(FirstArgumentOnNewline, Force)

  """foo(a,
    |    b)""" ==>
  """foo(
    |  a,
    |  b)"""

  """foo(a = 1,
    |    bb=2)""" ==>
  """foo(
    |  a = 1,
    |  bb = 2)"""
  }

  {
  implicit val formattingPreferences =
    FormattingPreferences.setPreference(FirstArgumentOnNewline, Preserve).setPreference(AlignArguments, true)

  // Parameters on a single line should not align to other parameters
  "foo(a = 1, bb = 2, ccc = 3)" ==>
  "foo(a = 1, bb = 2, ccc = 3)" // a: bad align, bb: ok, ccc: ok
  "foo(aa = 2, bbb = 3, c = 1)" ==>
  "foo(aa = 2, bbb = 3, c = 1)" // aa: bad align, bbb: ok, c: ok
  "foo(aa = 2, b = 1, ccc = 3)" ==>
  "foo(aa = 2, b = 1, ccc = 3)" // aa: bad align, b: ok, ccc: ok
  "foo(aaa = 3, b = 1, cc = 2)" ==>
  "foo(aaa = 3, b = 1, cc = 2)" // aaa: ok, b: ok, cc: ok

  // Parameters left on the first line should have other parameters aligned to them.
  """foo(a  = 1,
    |    bb =  2,
    |ccc=3)""" ==>
  """foo(a   = 1,
    |    bb  = 2,
    |    ccc = 3)"""

  """foo(a,
    |    bb,
    |ccc)""" ==>
  """foo(a,
    |    bb,
    |    ccc)"""

  // Parameters on the next line should also be aligned properly.
  """foo(
    |a  = 1,
    |    bb =  2,
    | ccc=3)""" ==>
  """foo(
    |  a   = 1,
    |  bb  = 2,
    |  ccc = 3)"""

  """foo(
    | a,
    |    bb,
    |ccc)""" ==>
  """foo(
    |  a,
    |  bb,
    |  ccc)"""
  }

  {
  implicit val formattingPreferences =
    FormattingPreferences.setPreference(FirstArgumentOnNewline, Prevent).setPreference(AlignArguments, true)

  // Parameters on the next line should be pulled to the first line, and aligned.
  """foo(
    |a  = 1,
    |    bb =  2,
    | ccc=3)""" ==>
  """foo(a   = 1,
    |    bb  = 2,
    |    ccc = 3)"""

  """foo(
    | a,
    |    bb,
    |ccc)""" ==>
  """foo(a,
    |    bb,
    |    ccc)"""
  }

  //  format: ON

  override val debug = false
}
